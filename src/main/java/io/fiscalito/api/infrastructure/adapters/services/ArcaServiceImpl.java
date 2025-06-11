package io.fiscalito.api.infrastructure.adapters.services;

import io.fiscalito.api.application.ports.inbound.service.ArcaService;
import io.fiscalito.api.application.ports.outbound.client.arca.WsfeClient;
import io.fiscalito.api.domain.arca.wsfev1.CbteTipo;
import io.fiscalito.api.domain.arca.wsfev1.DocTipo;
import io.fiscalito.api.domain.arca.wsfev1.Moneda;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArcaServiceImpl implements ArcaService {

    private final WsfeClient wsfeClient;
    private final InvoicePdfGenerator invoicePdfGenerator;


    @Override
    public void createInvoice(io.fiscalito.api.application.command.CreateInvoiceCommand createInvoiceCommand) throws Exception {
        var puntosDeVenta=wsfeClient.getPuntosVenta();
        var result = wsfeClient.emitInvoice(createInvoiceCommand);
        List<CbteTipo> tiposCbte = wsfeClient.getTiposCbte();
        List<DocTipo> tiposDoc = wsfeClient.getTiposDocumento();
        List<Moneda> monedas = wsfeClient.getTiposMoneda();
        System.out.println("Factura emitida: " + result);
        byte[] pdfBytes = invoicePdfGenerator.generateFromFECAEResponse(result);
        Files.write(Path.of("factura.pdf"), pdfBytes); // Si querés guardarlo en disco
    }
}
