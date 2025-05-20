package online.tufactura.api.domain.models.flow.states.SignUpFlow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.tufactura.api.application.ports.outbound.client.WhatsappClient;
import online.tufactura.api.domain.models.FlowContext;
import online.tufactura.api.domain.models.flow.FlowCommand;
import online.tufactura.api.domain.models.flow.FlowState;
import online.tufactura.api.domain.models.flow.MessageType;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSignUpState implements FlowState {
    private final WhatsappClient whatsappClient;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Override
    public void handle(FlowContext context, FlowCommand command) {
        log.debug("Handling email collection state for: {}", command.getFrom());
        
        if ( MessageType.TEXT.name().equals(command.getType().name())) {
            String email = command.getPayload();
            if (isValidEmail(email)) {
                // Store email in context data
                context.setData(email);
                context.setCurrentState("SIGN_UP_COMPANY");
                context.setPreviousState("SIGN_UP_EMAIL");
                whatsappClient.sendMessage(command.getFrom(), 
                    "Gracias. Ahora, por favor ingresa el nombre de tu empresa.");
            } else {
                whatsappClient.sendMessage(command.getFrom(), 
                    "Por favor, ingresa un email válido.");
            }
        } else {
            whatsappClient.sendMessage(command.getFrom(), 
                "Por favor, envía un mensaje de texto con tu email.");
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    @Override
    public String getStateName() {
        return "SIGN_UP_EMAIL";
    }
} 