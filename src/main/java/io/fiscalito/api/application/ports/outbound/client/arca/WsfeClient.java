package io.fiscalito.api.application.ports.outbound.client.arca;

import io.fiscalito.api.application.command.CreateInvoiceCommand;
import io.fiscalito.api.domain.arca.TokenAuthorization;
import io.fiscalito.api.domain.arca.wsfev1.FECAEResponse;

public interface WsfeClient {
    FECAEResponse emitInvoice(CreateInvoiceCommand createInvoiceCommand, TokenAuthorization tokenAuthorization) throws Exception;
}
