package online.tufactura.api.infrastructure.adapters.workflow.states;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.tufactura.api.application.ports.outbound.client.WhatsappClient;
import online.tufactura.api.domain.FlowContext;
import online.tufactura.api.domain.flow.FlowCommand;
import online.tufactura.api.application.ports.inbound.workflow.FlowState;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialState implements FlowState {
    private final WhatsappClient whatsappClient;

    @Override
    public void handle(FlowContext context, FlowCommand command) {
        log.debug("Handling command in InitialState for phone number: {}", command.getFrom());

        if ("AUDIO".equals(command.getType())) {
            // Process audio message
            context.setCurrentState("PROCESSING_AUDIO");
            context.setPreviousState("INITIAL");
            whatsappClient.sendMessage(command.getFrom(), "Procesando tu mensaje de audio...");
        } else {
            // Handle other types of messages
            whatsappClient.sendMessage(command.getFrom(),
                    "Por favor, envía un mensaje de audio con los detalles de la factura.");
        }
    }

    @Override
    public String getStateName() {
        return "INITIAL";
    }
} 