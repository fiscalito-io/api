package online.tufactura.api.domain.models.flow.states;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.tufactura.api.domain.models.FlowContext;
import online.tufactura.api.domain.models.flow.FlowCommand;
import online.tufactura.api.domain.models.flow.FlowState;
import online.tufactura.api.domain.ports.outbound.client.WhatsappClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialState implements FlowState {
    private final WhatsappClient whatsappClient;

    @Override
    public void handle(FlowContext context, FlowCommand command) {
        log.debug("Handling command in InitialState for phone number: {}", command.getPhoneNumber());
        
        if ("AUDIO".equals(command.getType())) {
            // Process audio message
            context.setCurrentState("PROCESSING_AUDIO");
            context.setPreviousState("INITIAL");
            whatsappClient.sendMessage(command.getPhoneNumber(), "Procesando tu mensaje de audio...");
        } else {
            // Handle other types of messages
            whatsappClient.sendMessage(command.getPhoneNumber(), 
                "Por favor, envía un mensaje de audio con los detalles de la factura.");
        }
    }

    @Override
    public String getStateName() {
        return "INITIAL";
    }
} 