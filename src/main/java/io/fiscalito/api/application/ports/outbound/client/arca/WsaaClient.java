package io.fiscalito.api.application.ports.outbound.client.arca;

import io.fiscalito.api.domain.arca.TokenAuthorization;

public interface WsaaClient {
    TokenAuthorization getToken() throws Exception;
}
