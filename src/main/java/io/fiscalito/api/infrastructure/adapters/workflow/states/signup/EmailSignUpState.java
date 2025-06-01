package io.fiscalito.api.infrastructure.adapters.workflow.states.signup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.fiscalito.api.application.ports.outbound.client.WhatsappClient;
import io.fiscalito.api.domain.flow.FlowContext;
import io.fiscalito.api.domain.flow.FlowStateEnum;
import io.fiscalito.api.domain.flow.FlowCommand;
import io.fiscalito.api.application.ports.inbound.workflow.FlowState;
import io.fiscalito.api.domain.flow.MessageType;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static io.fiscalito.api.domain.flow.FlowStateEnum.SIGN_UP_COMPANY_NAME;
import static io.fiscalito.api.domain.flow.FlowStateEnum.SIGN_UP_EMAIL;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSignUpState implements FlowState {
    private final WhatsappClient whatsappClient;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Override
    public FlowContext handle(FlowContext context, FlowCommand command) {
        log.debug("Handling email collection state for: {}", command.getFrom());
        
        if ( MessageType.TEXT.name().equals(command.getType().name())) {
            String email = command.getPayload();
            if (isValidEmail(email)) {
                // Store email in context data
                context.setData(email);
                context.setCurrentState(SIGN_UP_COMPANY_NAME);
                context.setPreviousState(SIGN_UP_EMAIL);
                whatsappClient.sendMessage(command.getFrom(), 
                    "Gracias. Ahora, por favor ingresa tu nombre o razon social.");
            } else {
                whatsappClient.sendMessage(command.getFrom(), 
                    "Por favor, ingresa un email válido.");
            }
        } else {
            whatsappClient.sendMessage(command.getFrom(), 
                "Por favor, envía un mensaje de texto con tu email.");
        }
        return context;
    }

    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    @Override
    public FlowStateEnum getFlowState() {
        return SIGN_UP_EMAIL;
    }
} 