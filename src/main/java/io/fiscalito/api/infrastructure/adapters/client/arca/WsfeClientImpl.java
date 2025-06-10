package io.fiscalito.api.infrastructure.adapters.client.arca;

import io.fiscalito.api.application.command.CreateInvoiceCommand;
import io.fiscalito.api.application.ports.outbound.client.arca.WsaaClient;
import io.fiscalito.api.application.ports.outbound.client.arca.WsfeClient;
import io.fiscalito.api.domain.arca.TokenAuthorization;
import io.fiscalito.api.domain.arca.wsfev1.ArrayOfFECAEDetRequest;
import io.fiscalito.api.domain.arca.wsfev1.CbteTipo;
import io.fiscalito.api.domain.arca.wsfev1.ConceptoTipo;
import io.fiscalito.api.domain.arca.wsfev1.Cotizacion;
import io.fiscalito.api.domain.arca.wsfev1.DocTipo;
import io.fiscalito.api.domain.arca.wsfev1.FEAuthRequest;
import io.fiscalito.api.domain.arca.wsfev1.FECAECabRequest;
import io.fiscalito.api.domain.arca.wsfev1.FECAEDetRequest;
import io.fiscalito.api.domain.arca.wsfev1.FECAERequest;
import io.fiscalito.api.domain.arca.wsfev1.FECAEResponse;
import io.fiscalito.api.domain.arca.wsfev1.FERecuperaLastCbteResponse;
import io.fiscalito.api.domain.arca.wsfev1.IvaTipo;
import io.fiscalito.api.domain.arca.wsfev1.Moneda;
import io.fiscalito.api.domain.arca.wsfev1.PtoVenta;
import io.fiscalito.api.domain.arca.wsfev1.Service;
import io.fiscalito.api.domain.arca.wsfev1.ServiceSoap;
import io.fiscalito.api.domain.arca.wsfev1.TributoTipo;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class WsfeClientImpl implements WsfeClient {

    private final WsaaClient wsaaClient;

    @Override
    public FECAEResponse emitInvoice(CreateInvoiceCommand createInvoiceCommand, TokenAuthorization tokenAuthorization) throws Exception {
        Service service = new Service();
        ServiceSoap port = service.getServiceSoap();

        FEAuthRequest auth = new FEAuthRequest();
        auth.setCuit(Long.parseLong(tokenAuthorization.getCuit()));
        auth.setSign(tokenAuthorization.getSign());
        auth.setToken(tokenAuthorization.getToken());

        int puntoVenta = 1;
        int tipoComprobante = 11;
        FERecuperaLastCbteResponse lastCbte = port.feCompUltimoAutorizado(auth, puntoVenta, tipoComprobante);
        int nuevoNumero = lastCbte.getCbteNro() + 1;

        FECAEDetRequest detalle = new FECAEDetRequest();
        detalle.setConcepto(1); // Productos
        detalle.setDocTipo(80); // CUIT
        if (createInvoiceCommand.getToTaxId() != null) {
            detalle.setDocNro(Long.parseLong(createInvoiceCommand.getToTaxId()));
        }
        detalle.setCbteDesde(nuevoNumero);
        detalle.setCbteHasta(nuevoNumero);
        detalle.setCbteFch(getActualDate());
        detalle.setImpTotal(createInvoiceCommand.getAmount().doubleValue());
        detalle.setImpNeto(createInvoiceCommand.getAmount().doubleValue());
        detalle.setImpIVA(0.00);
        detalle.setMonId("PES");
        detalle.setMonCotiz(1.00);
        FECAERequest req = new FECAERequest();
        FECAECabRequest cabecera = new FECAECabRequest();
        cabecera.setCantReg(1);
        cabecera.setCbteTipo(tipoComprobante);
        cabecera.setPtoVta(puntoVenta);
        req.setFeCabReq(cabecera);

        ArrayOfFECAEDetRequest array = new ArrayOfFECAEDetRequest();
        array.getFECAEDetRequest().add(detalle);
        req.setFeDetReq(array);
        return port.fecaeSolicitar(auth, req);
    }

    @Override
    public List<TributoTipo> getTiposTributo() {
        try {
            Service service = new Service();
            ServiceSoap port = service.getServiceSoap();
            TokenAuthorization token = wsaaClient.getToken();

            FEAuthRequest auth = new FEAuthRequest();
            auth.setCuit(Long.parseLong(token.getCuit()));
            auth.setSign(token.getSign());
            auth.setToken(token.getToken());

            return port.feParamGetTiposTributos(auth).getResultGet().getTributoTipo();
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo tipos de tributo", e);
        }
    }

    @Override
    public List<IvaTipo> getTiposIva() {
        try {
            Service service = new Service();
            ServiceSoap port = service.getServiceSoap();
            TokenAuthorization token = wsaaClient.getToken();

            FEAuthRequest auth = new FEAuthRequest();
            auth.setCuit(Long.parseLong(token.getCuit()));
            auth.setSign(token.getSign());
            auth.setToken(token.getToken());

            return port.feParamGetTiposIva(auth).getResultGet().getIvaTipo();
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo tipos de IVA", e);
        }
    }

    @Override
    public List<CbteTipo> getTiposCbte() {
        try {
            Service service = new Service();
            ServiceSoap port = service.getServiceSoap();
            TokenAuthorization token = wsaaClient.getToken();

            FEAuthRequest auth = new FEAuthRequest();
            auth.setCuit(Long.parseLong(token.getCuit()));
            auth.setSign(token.getSign());
            auth.setToken(token.getToken());

            return port.feParamGetTiposCbte(auth).getResultGet().getCbteTipo();
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo tipos de comprobante", e);
        }
    }

    @Override
    public List<Moneda> getTiposMoneda() {
        try {
            Service service = new Service();
            ServiceSoap port = service.getServiceSoap();
            TokenAuthorization token = wsaaClient.getToken();

            FEAuthRequest auth = new FEAuthRequest();
            auth.setCuit(Long.parseLong(token.getCuit()));
            auth.setSign(token.getSign());
            auth.setToken(token.getToken());

            return port.feParamGetTiposMonedas(auth).getResultGet().getMoneda();
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo tipos de moneda", e);
        }
    }

    @Override
    public List<DocTipo> getTiposDocumento() {
        try {
            Service service = new Service();
            ServiceSoap port = service.getServiceSoap();
            TokenAuthorization token = wsaaClient.getToken();

            FEAuthRequest auth = new FEAuthRequest();
            auth.setCuit(Long.parseLong(token.getCuit()));
            auth.setSign(token.getSign());
            auth.setToken(token.getToken());

            return port.feParamGetTiposDoc(auth).getResultGet().getDocTipo();
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo tipos de documento", e);
        }
    }

    @Override
    public List<ConceptoTipo> getTiposConcepto() {
        try {
            Service service = new Service();
            ServiceSoap port = service.getServiceSoap();
            TokenAuthorization token = wsaaClient.getToken();

            FEAuthRequest auth = new FEAuthRequest();
            auth.setCuit(Long.parseLong(token.getCuit()));
            auth.setSign(token.getSign());
            auth.setToken(token.getToken());
            return port.feParamGetTiposConcepto(auth).getResultGet().getConceptoTipo();
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo tipos de concepto", e);
        }
    }

    @Override
    public List<PtoVenta> getPuntosVenta() {
        try {
            Service service = new Service();
            ServiceSoap port = service.getServiceSoap();
            TokenAuthorization token = wsaaClient.getToken();

            FEAuthRequest auth = new FEAuthRequest();
            auth.setCuit(Long.parseLong(token.getCuit()));
            auth.setSign(token.getSign());
            auth.setToken(token.getToken());

            return port.feParamGetPtosVenta(auth).getResultGet().getPtoVenta();
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo puntos de venta", e);
        }
    }

    @Override
    public Cotizacion getCotizacion(String monedaId) {
        try {
            Service service = new Service();
            ServiceSoap port = service.getServiceSoap();
            TokenAuthorization token = wsaaClient.getToken();

            FEAuthRequest auth = new FEAuthRequest();
            auth.setCuit(Long.parseLong(token.getCuit()));
            auth.setSign(token.getSign());
            auth.setToken(token.getToken());
            return port.feParamGetCotizacion(auth, monedaId, getActualDate()).getResultGet();
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo cotización para " + monedaId, e);
        }
    }

    private String getActualDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private String responseToXml(FECAEResponse response) {
        try {
            JAXBContext context = JAXBContext.newInstance(FECAEResponse.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter writer = new StringWriter();
            marshaller.marshal(response, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo serializar la respuesta", e);
        }
    }
}
