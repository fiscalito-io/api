package online.tufactura.api.infrastructure.adapters.client.arca;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPConnection;
import jakarta.xml.soap.SOAPConnectionFactory;
import jakarta.xml.soap.SOAPMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.tufactura.api.application.ports.outbound.client.arca.WsaaClient;
import online.tufactura.api.domain.ArcaConstants;
import online.tufactura.api.domain.arca.TokenAuthorization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

@Component
@Slf4j
@RequiredArgsConstructor
public class WsaaClientImpl implements WsaaClient {

    @Value("${arca.wsaa.p12.path}")
    private String p12Path;
    @Value("${arca.wsaa.p12.path}")
    private String p12Password;
    @Value("${arca.cuit}")
    private String cuit;


    public TokenAuthorization getToken() throws Exception {
        String tra = buildTRA();
        byte[] signedCms = signTRA(tra);
        String soapRequest = buildSOAPRequest(signedCms);

        // Enviar al WSAA
        SOAPMessage soapResponse = sendSOAPRequest(soapRequest, ArcaConstants.WSAA_URL);
        return parseTA(soapResponse);
    }

    private String buildTRA() {
        return """
        <loginTicketRequest version="1.0">
            <header>
                <uniqueId>"""
                    +System.currentTimeMillis()/1000+ """
                </uniqueId>
                <generationTime>""" + java.time.OffsetDateTime.now().minusMinutes(5) + """
                </generationTime>
                <expirationTime>""" + java.time.OffsetDateTime.now().plusMinutes(5) + """
            </expirationTime>
            </header>
            <service>"""
                + ArcaConstants.SERVICE + """
            </service>
        </loginTicketRequest>
        """;
    }

    private byte[] signTRA(String traXml) throws Exception {
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new FileInputStream(p12Path), p12Password.toCharArray());
        String alias = ks.aliases().nextElement();
        PrivateKey privateKey = (PrivateKey) ks.getKey(alias, p12Password.toCharArray());
        X509Certificate cert = (X509Certificate) ks.getCertificate(alias);

        // Firma el XML con BouncyCastle o lib propia...
        // Simulación para este ejemplo
        return traXml.getBytes();
    }

    private String buildSOAPRequest(byte[] cms) {
        String base64Cms = java.util.Base64.getEncoder().encodeToString(cms);
        return """
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:wsaa="http://wsaa.view.sua.dvadac.desein.afip.gov">
          <soapenv:Header/>
          <soapenv:Body>
            <wsaa:loginCms>
              <wsaa:in0>"""
                + base64Cms + """
              </wsaa:in0>
            </wsaa:loginCms>
          </soapenv:Body>
        </soapenv:Envelope>
        """;
    }

    private SOAPMessage sendSOAPRequest(String soapRequestXml, String endpoint) throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage(null, new ByteArrayInputStream(soapRequestXml.getBytes()));
        SOAPConnection connection = SOAPConnectionFactory.newInstance().createConnection();
        return connection.call(message, endpoint);
    }

    private TokenAuthorization parseTA(SOAPMessage response) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        response.writeTo(baos);
        String xml = baos.toString();

        // Extraer token y sign (en un caso real usar XML parser)
        String token = xml.split("<token>")[1].split("</token>")[0];
        String sign = xml.split("<sign>")[1].split("</sign>")[0];
        return new TokenAuthorization(token, sign, cuit);
    }
}
