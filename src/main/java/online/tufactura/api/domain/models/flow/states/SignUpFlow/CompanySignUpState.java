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
public class CompanySignUpState implements FlowState {
    private final WhatsappClient whatsappClient;

    @Override
    public void handle(FlowContext context, FlowCommand command) {
        log.debug("Handling company name collection state for: {}", command.getFrom());
        
        if (MessageType.TEXT.name().equals(command.getType().name())) {
            // Store company name in context data
            context.setData(command.getPayload());
            context.setCurrentState("SIGN_UP_COMPLETE");
            context.setPreviousState("SIGN_UP_COMPANY");
            whatsappClient.sendMessage(command.getFrom(), 
                "¡Gracias por registrarte! Ahora puedes comenzar a usar Facturitas. " +
                "Para crear una factura, simplemente envía un mensaje con los detalles.");
        } else {
            whatsappClient.sendMessage(command.getFrom(), 
                "Por favor, envía un mensaje de texto con el nombre de tu empresa.");
        }
    }

    @Override
    public String getStateName() {
        return "SIGN_UP_COMPANY";
    }
} 