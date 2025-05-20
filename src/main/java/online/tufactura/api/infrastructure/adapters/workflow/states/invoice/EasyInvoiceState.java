package online.tufactura.api.infrastructure.adapters.workflow.states.invoice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import online.tufactura.api.application.ports.inbound.workflow.FlowState;
import online.tufactura.api.application.ports.outbound.client.arca.ArcaClient;
import online.tufactura.api.application.ports.outbound.client.WhatsappClient;
import online.tufactura.api.domain.FlowContext;
import online.tufactura.api.domain.flow.FlowCommand;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EasyInvoiceState implements FlowState {

    private final ArcaClient arcaClient;
    private final WhatsappClient whatsAppSender;


    @Override
    public void handle(FlowContext context, FlowCommand command) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode dataNode = mapper.readTree(context.getData());

            String taxId = dataNode.get("taxId").asText();
            String invoiceType = dataNode.get("invoiceType").asText();
            String from = dataNode.get("from").asText();

            byte[] pdfInvoice = arcaClient.createInvoice(taxId, invoiceType, from);
            whatsAppSender.sendPdf(context.getPhoneNumber(), pdfInvoice, "factura.pdf");

            context.setCurrentState("INVOICE_SENT");

        } catch (Exception e) {
            //TODO handle here
            e.printStackTrace();
        }
    }

    @Override
    public String getStateName() {
        return "EASY_INVOICE";
    }
}
