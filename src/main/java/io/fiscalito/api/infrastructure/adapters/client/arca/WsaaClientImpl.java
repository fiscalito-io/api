package io.fiscalito.api.infrastructure.adapters.client.arca;

import io.fiscalito.api.application.ports.outbound.client.arca.WsaaClient;
import io.fiscalito.api.domain.arca.ArcaConstants;
import io.fiscalito.api.domain.arca.TokenAuthorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class WsaaClientImpl implements WsaaClient {

    private final StringRedisTemplate redisTemplate;


    @Value("${arca.p12.base64}")
    private String p12Base64;

    @Value("${arca.p12.password}")
    private String password;

    @Value("${arca.cuit}")
    private String cuit;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String TOKEN_KEY = "arca:token";

    @Override
    public TokenAuthorization getToken() throws Exception {
        String cached = redisTemplate.opsForValue().get(TOKEN_KEY);
        if (cached != null) {
            String[] parts = cached.split("\\|");
            return new TokenAuthorization(parts[0], parts[1], cuit);
        }
        String tra = buildTRA();
        log.debug("TRA XML:\n{}", tra);

        byte[] signedCms = signTRA(tra);
        log.debug("CMS generated. Length: {}", signedCms.length);

        String soapRequest = buildSOAPRequest(signedCms);
        String soapResponse = sendRawSoapRequest(soapRequest, ArcaConstants.WSAA_URL);

        log.debug("SOAP Response:\n{}", soapResponse);
        var response = parseSoapResponse(soapResponse);
        redisTemplate.opsForValue().set(
                TOKEN_KEY,
                response.getToken() + "|" + response.getSign(),
                10, TimeUnit.MINUTES
        );
        return response;
    }

    private String buildTRA() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        String generationTime = now.minusMinutes(5).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        String expirationTime = now.plusMinutes(5).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        long uniqueId = System.currentTimeMillis() / 1000;

        return """
            <loginTicketRequest version="1.0">
                <header>
                    <uniqueId>%d</uniqueId>
                    <generationTime>%s</generationTime>
                    <expirationTime>%s</expirationTime>
                </header>
                <service>%s</service>
            </loginTicketRequest>
            """.formatted(uniqueId, generationTime, expirationTime, ArcaConstants.SERVICE);
    }

    private byte[] signTRA(String traXml) throws Exception {
        byte[] p12Bytes;
        try {
            p12Bytes = Base64.getDecoder().decode(p12Base64);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El certificado .p12 está mal codificado en base64", e);
        }

        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new ByteArrayInputStream(p12Bytes), password.toCharArray());
        String alias = ks.aliases().nextElement();

        PrivateKey privateKey = (PrivateKey) ks.getKey(alias, password.toCharArray());
        X509Certificate cert = (X509Certificate) ks.getCertificate(alias);

        CMSProcessableByteArray msg = new CMSProcessableByteArray(traXml.getBytes(StandardCharsets.UTF_8));
        CMSSignedDataGenerator gen = new CMSSignedDataGenerator();

        gen.addSignerInfoGenerator(
                new JcaSignerInfoGeneratorBuilder(
                        new JcaDigestCalculatorProviderBuilder().build()
                ).build(
                        new JcaContentSignerBuilder("SHA256withRSA").build(privateKey),
                        cert
                )
        );
        gen.addCertificate(new JcaX509CertificateHolder(cert));

        CMSSignedData signed = gen.generate(msg, true);
        return signed.getEncoded();
    }

    private String buildSOAPRequest(byte[] cms) {
        String base64Cms = Base64.getEncoder().encodeToString(cms).replaceAll("\r|\n", "");
        return """
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:wsaa="http://wsaa.view.sua.dvadac.desein.afip.gov">
              <soapenv:Header/>
              <soapenv:Body>
                <wsaa:loginCms>
                  <wsaa:in0>%s</wsaa:in0>
                </wsaa:loginCms>
              </soapenv:Body>
            </soapenv:Envelope>
            """.formatted(base64Cms);
    }

    private String sendRawSoapRequest(String soapXml, String endpoint) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_XML);
            headers.set("SOAPAction", "");

            HttpEntity<String> request = new HttpEntity<>(soapXml, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(endpoint, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            log.error("Error during WSAA request", e);
            throw new RuntimeException("WSAA request failed: " + e.getMessage(), e);
        }
    }

    private TokenAuthorization parseSoapResponse(String soapXml) throws Exception {
        String escapedXml = extractFromXml(soapXml, "loginCmsReturn");
        String innerXml = org.apache.commons.text.StringEscapeUtils.unescapeXml(escapedXml);
        String token = extractFromXml(innerXml, "token");
        String sign = extractFromXml(innerXml, "sign");
        return new TokenAuthorization(token, sign, cuit);
    }

    private String extractFromXml(String xml, String tagName) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)));
        var nodeList = doc.getElementsByTagName(tagName);
        if (nodeList.getLength() == 0) {
            throw new RuntimeException("No se encontró el tag <" + tagName + "> en la respuesta");
        }
        return nodeList.item(0).getTextContent();
    }
}
