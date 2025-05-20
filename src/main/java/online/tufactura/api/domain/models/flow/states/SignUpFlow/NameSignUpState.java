package online.tufactura.api.domain.models.flow.states.SignUpFlow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.tufactura.api.application.ports.outbound.client.WhatsappClient;
import online.tufactura.api.domain.models.FlowContext;
import online.tufactura.api.domain.models.flow.FlowCommand;
import online.tufactura.api.domain.models.flow.FlowState;
import online.tufactura.api.domain.models.flow.MessageType;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NameSignUpState implements FlowState {
    private final WhatsappClient whatsappClient;

    @Override
    public void handle(FlowContext context, FlowCommand command) {
        log.debug("Handling name collection state for: {}", command.getFrom());
        
        if (command.getType().name().equals(MessageType.TEXT.name()) ) {
            // Store name in context data
            context.setData(command.getPayload());
            context.setCurrentState("SIGN_UP_EMAIL");
            context.setPreviousState("SIGN_UP_NAME");
            whatsappClient.sendMessage(command.getFrom(), 
                "Gracias. Ahora, por favor ingresa tu email.");
        } else {
            whatsappClient.sendMessage(command.getFrom(), 
                "Por favor, envía un mensaje de texto con tu nombre completo.");
        }
    }

    @Override
    public String getStateName() {
        return "SIGN_UP_NAME";
    }
} 