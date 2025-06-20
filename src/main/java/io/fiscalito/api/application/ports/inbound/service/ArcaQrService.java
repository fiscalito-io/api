package io.fiscalito.api.application.ports.inbound.service;

import java.awt.image.BufferedImage;

public interface ArcaQrService {
    BufferedImage generateQrImageFromFacturaJson(String jsonPayload) throws Exception;
}
