package io.fiscalito.api.infrastructure.adapters.client.arca;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.fiscalito.api.application.ports.outbound.client.arca.ArcaClient;
import io.fiscalito.api.application.ports.outbound.client.arca.WsaaClient;
import io.fiscalito.api.application.ports.outbound.client.arca.WsfeClient;
import io.fiscalito.api.domain.arca.TokenAuthorization;
import org.springframework.stereotype.Component;

//TODO rename this as a service

@Component
@RequiredArgsConstructor
@Slf4j
public class ArcaClientImpl implements ArcaClient {

    private final WsaaClient wsaaClient;
    private final WsfeClient wsfeClient;

    public byte[] createInvoice(String cuitRepresentado, String invoiceType, String from) {
        try {
            TokenAuthorization auth = wsaaClient.getToken();
            return wsfeClient.emitInvoice(auth, cuitRepresentado);
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[]{}; // TODO o manejar el error de otra manera
        }
    }
}
