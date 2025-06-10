package io.fiscalito.api.infrastructure.adapters.services;

import io.fiscalito.api.application.ports.inbound.service.ArcaService;
import io.fiscalito.api.application.ports.outbound.client.arca.WsaaClient;
import io.fiscalito.api.application.ports.outbound.client.arca.WsfeClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArcaServiceImpl implements ArcaService {

    private final WsfeClient wsfeClient;
    private final WsaaClient wsaaClient;
    private final InvoicePdfGenerator invoicePdfGenerator;


    @Override
    public void createInvoice(io.fiscalito.api.application.command.CreateInvoiceCommand createInvoiceCommand) throws Exception {
        var tokenAuthorization=wsaaClient.getToken();
        var result = wsfeClient.emitInvoice(createInvoiceCommand, tokenAuthorization);
        List<TipoCbte> tiposCbte = port.feParamGetTiposCbte(auth).getResult().getCbteTipo();
        List<TipoDoc> tiposDoc = port.feParamGetTiposDoc(auth).getResult().getDocTipo();
        List<Moneda> monedas = port.feParamGetTiposMonedas(auth).getResult().getMoneda();
        System.out.println("Factura emitida: " + result);
        byte[] pdfBytes = invoicePdfGenerator.generateFromFECAEResponse(result);
        Files.write(Path.of("factura.pdf"), pdfBytes); // Si querés guardarlo en disco
    }
}
