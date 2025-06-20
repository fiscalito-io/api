package io.fiscalito.api.infrastructure.adapters.services;

import io.fiscalito.api.application.command.CreateInvoiceCommand;
import io.fiscalito.api.domain.arca.ArcaInvoiceItem;
import io.fiscalito.api.domain.arca.wsfev1.FECAEDetResponse;
import io.fiscalito.api.domain.arca.wsfev1.FECAEResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class InvoicePdfGenerator {

    public byte[] generateInvoicePdf(
            CreateInvoiceCommand command,
            FECAEResponse response,
            BufferedImage qrImage,
            List<ArcaInvoiceItem> items,
            String razonSocialEmisor,
            String domicilioEmisor,
            String condicionIVAEmisor,
            String cuitReceptor,
            String nombreReceptor,
            String domicilioReceptor,
            String condicionIVAReceptor,
            String condicionVenta) throws Exception {

        FECAEDetResponse detalle = response.getFeDetResp().getFECAEDetResponse().get(0);

        // Cargar el archivo Jasper compilado
        InputStream jasperStream = getClass().getResourceAsStream("/reports/invoice_template.jasper");
        if (jasperStream == null) throw new RuntimeException("No se encontró el reporte Jasper compilado");

        JasperReport report = (JasperReport) JRLoader.loadObject(jasperStream);

        Map<String, Object> params = new HashMap<>();
        params.put("CAE", detalle.getCAE());
        params.put("CAE_VTO", detalle.getCAEFchVto());
        params.put("PTO_VTA", String.format("%04d", response.getFeCabResp().getPtoVta()));
        params.put("NUMERO", String.format("%08d", detalle.getCbteDesde()));
        params.put("FECHA", detalle.getCbteFch());
        params.put("CUIT_EMISOR", "20382560966"); // hardcoded or dynamic

        // Emisor
        params.put("RAZON_EMISOR", razonSocialEmisor);
        params.put("DOM_EMISOR", domicilioEmisor);
        params.put("IVA_EMISOR", condicionIVAEmisor);

        // Receptor
        params.put("CUIT_RECEPTOR", cuitReceptor);
        params.put("NOMBRE_RECEPTOR", nombreReceptor);
        params.put("DOM_RECEPTOR", domicilioReceptor);
        params.put("IVA_RECEPTOR", condicionIVAReceptor);
        params.put("COND_VENTA", condicionVenta);

        // Totales
        params.put("TOTAL", command.getAmount().doubleValue());
        params.put("SUBTOTAL", command.getAmount().doubleValue());
        params.put("OTROS_TRIBUTOS", 0.00);

        // QR
        params.put("QR_IMAGE", qrImage);

        JasperPrint print = JasperFillManager.fillReport(
                report,
                params,
                new JRBeanCollectionDataSource(items)
        );

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            JasperExportManager.exportReportToPdfStream(print, out);
            return out.toByteArray();
        }
    }
}
