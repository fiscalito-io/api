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
public class InitialSignUpState implements FlowState {
    private final WhatsappClient whatsappClient;

    @Override
    public void handle(FlowContext context, FlowCommand command) {
        log.debug("Handling initial sign-up state for: {}", command.getFrom());

        if (MessageType.TEXT.name().equals(command.getType().name())) {
            context.setCurrentState("SIGN_UP_NAME");
            context.setPreviousState("INITIAL");
            whatsappClient.sendMessage(command.getFrom(),
                    "¡Bienvenido! Para comenzar, por favor ingresa tu nombre completo.");
        } else {
            whatsappClient.sendMessage(command.getFrom(),
                    "Por favor, envía un mensaje de texto con tu nombre completo.");
        }
    }

    @Override
    public String getStateName() {
        return "INITIAL";
    }
} 