package online.tufactura.api.application.ports.outbound.client.arca;

import online.tufactura.api.domain.arca.TokenAuthorization;

public interface WsaaClient {
    TokenAuthorization getToken() throws Exception;
}
