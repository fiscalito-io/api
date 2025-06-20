package io.fiscalito.api.infrastructure.adapters.services;

import io.fiscalito.api.application.command.CreateInvoiceCommand;
import io.fiscalito.api.application.ports.inbound.service.ArcaQrService;
import io.fiscalito.api.application.ports.inbound.service.ArcaService;
import io.fiscalito.api.application.ports.outbound.client.arca.WsPadronA4Client;
import io.fiscalito.api.application.ports.outbound.client.arca.WsfeClient;
import io.fiscalito.api.domain.arca.ArcaInvoiceItem;
import io.fiscalito.api.domain.arca.wsfev1.CbteTipo;
import io.fiscalito.api.domain.arca.wsfev1.DocTipo;
import io.fiscalito.api.domain.arca.wsfev1.FECAEResponse;
import io.fiscalito.api.domain.arca.wsfev1.Moneda;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArcaServiceImpl implements ArcaService {

    private final WsfeClient wsfeClient;
    //private final WsPadronA4Client wsPadronA4CClient;
    private final InvoicePdfGenerator invoicePdfGenerator;
    private final ArcaQrService arcaQrService;


    @Override
    public void createInvoice(io.fiscalito.api.application.command.CreateInvoiceCommand createInvoiceCommand) throws Exception {
        var result = wsfeClient.emitInvoice(createInvoiceCommand);
        String qrJson = buildQrJsonFromResponse(createInvoiceCommand, result);
        var qrImage = arcaQrService.generateQrImageFromFacturaJson(qrJson);
        System.out.println("Factura emitida: " + result);

        byte[] pdfBytes = invoicePdfGenerator.generateInvoicePdf(createInvoiceCommand, result, qrImage, List.of(
                ArcaInvoiceItem.builder().build()
        ),
                "Sebastian Emanuel Enrique Bogado",
                "Constancio C. Vigil 832, La Reja, Buenos Aires, Argentina",
                "Monotributista",
                createInvoiceCommand.getToTaxId(),
                "",
                "Constancio C. Vigil 832, La Reja, Buenos Aires, Argentina",
                "Consumidor Final",
                "Contado"
                );
        Files.write(Path.of("factura3.pdf"), pdfBytes); // Si querés guardarlo en disco
    }



//    private Object getInformationFromTaxId(String toTaxId) {
//        if (toTaxId == null) {
//            return null;
//        }
//        return ""
//    }

    private String buildQrJsonFromResponse(CreateInvoiceCommand command, FECAEResponse response) {
        var detalle = response.getFeDetResp().getFECAEDetResponse().get(0);
        String arcaDate = detalle.getCbteFch(); // por ejemplo: "20250612"
        String formattedDate = LocalDate.parse(arcaDate, DateTimeFormatter.ofPattern("yyyyMMdd"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Map<String, Object> data = new HashMap<>();
        data.put("ver", 1);
        data.put("fecha", formattedDate); // formato: yyyy-MM-dd
        data.put("cuit", 20382560966L); // tu CUIT
        data.put("ptoVta", response.getFeCabResp().getPtoVta());
        data.put("tipoCmp", response.getFeCabResp().getCbteTipo());
        data.put("nroCmp", detalle.getCbteDesde());
        data.put("importe", Math.round(command.getAmount().doubleValue() * 100.0) / 100.0);
        data.put("moneda", "PES");
        data.put("ctz", 1.0);
        data.put("tipoDocRec", detalle.getDocTipo());
        data.put("nroDocRec", detalle.getDocNro());
        data.put("tipoCodAut", "E");
        data.put("codAut", Long.parseLong(detalle.getCAE()));

        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(data);
        } catch (Exception e) {
            throw new RuntimeException("Error generando JSON para QR", e);
        }
    }
}
