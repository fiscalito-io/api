package online.tufactura.api.application.ports.outbound.client.arca;

import online.tufactura.api.domain.arca.TokenAuthorization;

public interface WsfeClient {
    //TODO modify cuit representado por el del cliente y todos los datos (Asumo que va a pedir mas que eso)
    byte[] emitInvoice(TokenAuthorization auth, String cuitRepresentado);
}
