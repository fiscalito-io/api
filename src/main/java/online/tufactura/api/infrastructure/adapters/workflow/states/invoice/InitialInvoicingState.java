package online.tufactura.api.infrastructure.adapters.workflow.states.invoice;

import lombok.RequiredArgsConstructor;
import online.tufactura.api.application.ports.inbound.workflow.FlowState;
import online.tufactura.api.domain.FlowContext;
import online.tufactura.api.domain.flow.FlowCommand;
import org.springframework.stereotype.Component;

import static online.tufactura.api.domain.MessageType.TEXT;

@Component
@RequiredArgsConstructor
public class InitialInvoicingState implements FlowState {


    @Override
    public void handle(FlowContext context, FlowCommand command) {
        if (TEXT.equals(command.getType())) {
            context.setCurrentState("INITIAL_TEXT_INVOICE");
        } else {
            context.setCurrentState("INITIAL_AUDIO_INVOICE");
        }
    }

    @Override
    public String getStateName() {
        return "INITIAL_INVOICE";
    }
}
