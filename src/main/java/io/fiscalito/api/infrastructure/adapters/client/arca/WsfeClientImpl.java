package io.fiscalito.api.infrastructure.adapters.client.arca;

import io.fiscalito.api.application.ports.outbound.client.arca.WsfeClient;
import io.fiscalito.api.domain.arca.TokenAuthorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WsfeClientImpl implements WsfeClient {

    public byte[] emitInvoice(TokenAuthorization auth, String taxId) {
        log.info("Facturando en nombre de CUIT: " + taxId);
        log.debug("Usando token: " + auth.getToken());
        log.debug("Usando sign: " + auth.getSign());

        // Simulación: deberías armar el request XML completo
        return "PDF_SIMULADO_AFIP".getBytes(); // reemplazar por PDF generado real
    }
}
