package io.fiscalito.api.infrastructure.adapters.workflow.states.signup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.fiscalito.api.application.ports.outbound.client.WhatsappClient;
import io.fiscalito.api.domain.flow.FlowContext;
import io.fiscalito.api.domain.flow.FlowStateEnum;
import io.fiscalito.api.domain.flow.FlowCommand;
import io.fiscalito.api.application.ports.inbound.workflow.FlowState;
import org.springframework.stereotype.Component;

import static io.fiscalito.api.domain.flow.FlowStateEnum.SIGN_UP_COMPLETE;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompleteSignUpState implements FlowState {
    private final WhatsappClient whatsappClient;

    @Override
    public FlowContext handle(FlowContext context, FlowCommand command) {
        log.debug("Handling complete sign-up state for: {}", command.getFrom());
        
        // In this state, we just acknowledge the message and remind about invoice creation
        whatsappClient.sendMessage(command.getFrom(), 
            "Para crear una factura, envía un mensaje con los detalles del producto o servicio. " +
            "Por ejemplo: 'Factura por 1000 USD por desarrollo de software'");
        return context;
    }

    @Override
    public FlowStateEnum getFlowState() {
        return SIGN_UP_COMPLETE;
    }
} 