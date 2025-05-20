package online.tufactura.api.domain.models.flow.states.SignUpFlow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.tufactura.api.application.ports.outbound.client.WhatsappClient;
import online.tufactura.api.domain.models.FlowContext;
import online.tufactura.api.domain.models.flow.FlowCommand;
import online.tufactura.api.domain.models.flow.FlowState;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompleteSignUpState implements FlowState {
    private final WhatsappClient whatsappClient;

    @Override
    public void handle(FlowContext context, FlowCommand command) {
        log.debug("Handling complete sign-up state for: {}", command.getFrom());
        
        // In this state, we just acknowledge the message and remind about invoice creation
        whatsappClient.sendMessage(command.getFrom(), 
            "Para crear una factura, envía un mensaje con los detalles del producto o servicio. " +
            "Por ejemplo: 'Factura por 1000 USD por desarrollo de software'");
    }

    @Override
    public String getStateName() {
        return "SIGN_UP_COMPLETE";
    }
} 