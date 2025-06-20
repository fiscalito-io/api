package io.fiscalito.api.infrastructure.adapters.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import io.fiscalito.api.application.ports.inbound.service.ArcaQrService;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ArcaQrServiceImplZxing implements ArcaQrService {

    public BufferedImage generateQrImageFromFacturaJson(String jsonPayload) throws Exception {
        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(jsonPayload.getBytes(StandardCharsets.UTF_8));
        String qrUrl = "https://servicioscf.afip.gob.ar/publico/comprobantes/cae.aspx?p=" + encoded;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        var bitMatrix = qrCodeWriter.encode(qrUrl, BarcodeFormat.QR_CODE, 200, 200, hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
