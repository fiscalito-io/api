package io.fiscalito.api.infrastructure.adapters.workflow.states.invoice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.fiscalito.api.application.ports.inbound.workflow.FlowState;
import io.fiscalito.api.application.ports.outbound.client.WhatsappClient;
import io.fiscalito.api.application.ports.outbound.client.arca.ArcaClient;
import io.fiscalito.api.domain.flow.FlowContext;
import io.fiscalito.api.domain.flow.FlowStateEnum;
import io.fiscalito.api.domain.flow.FlowCommand;
import org.springframework.stereotype.Component;

import static io.fiscalito.api.domain.flow.FlowStateEnum.EASY_INVOICE;
import static io.fiscalito.api.domain.flow.FlowStateEnum.INVOICE_SENT;

@Component
@RequiredArgsConstructor
@Slf4j
public class EasyInvoiceState implements FlowState {

    private final ArcaClient arcaClient;
    private final WhatsappClient whatsAppSender;


    @Override
    public FlowContext handle(FlowContext context, FlowCommand command) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode dataNode = mapper.readTree(context.getData());
            String taxId = dataNode.get("taxId").asText();
            String invoiceType = dataNode.get("invoiceType").asText();
            String from = dataNode.get("from").asText();

            byte[] pdfInvoice = arcaClient.createInvoice(taxId, invoiceType, from);
            whatsAppSender.sendPdf(context.getPhoneNumber(), pdfInvoice, "factura.pdf");

            context.setCurrentState(INVOICE_SENT);
            return context;

        } catch (Exception e) {
            //TODO handle here
            log.error(e.getMessage(), e);
            return context;
        }
    }

    @Override
    public FlowStateEnum getFlowState() {
        return EASY_INVOICE;
    }
}
