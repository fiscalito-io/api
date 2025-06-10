package io.fiscalito.api.infrastructure.adapters.services;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.Paragraph;
import io.fiscalito.api.domain.arca.wsfev1.FECAEResponse;
import io.fiscalito.api.domain.arca.wsfev1.FECAEDetResponse;
import io.fiscalito.api.domain.arca.wsfev1.FECAECabResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@Slf4j
public class InvoicePdfGenerator {

    public byte[] generateFromFECAEResponse(FECAEResponse response) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            FECAECabResponse cabecera = response.getFeCabResp();
            FECAEDetResponse detalle = response.getFeDetResp().getFECAEDetResponse().get(0);

            document.add(new Paragraph("Factura electrónica"));
            document.add(new Paragraph("CAE: " + detalle.getCAE()));
            document.add(new Paragraph("Fecha de vencimiento CAE: " + detalle.getCAEFchVto()));
            document.add(new Paragraph("Número comprobante: " +
                    cabecera.getCbteTipo() + "-" +
                    String.format("%05d", cabecera.getPtoVta()) + "-" +
                    String.format("%08d", detalle.getCbteDesde())));
            document.add(new Paragraph("CUIT receptor: " + detalle.getDocNro()));
            document.add(new Paragraph("Importe total: $" + response.getFeCabResp().getCantReg()));

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            log.error("Error generando PDF", e);
            throw new RuntimeException("No se pudo generar el PDF de la factura", e);
        }
    }
}
