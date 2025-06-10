package io.fiscalito.api.application.ports.inbound.service;

import io.fiscalito.api.application.command.CreateInvoiceCommand;

public interface ArcaService {
    void createInvoice(CreateInvoiceCommand createInvoiceCommand) throws Exception;
}
