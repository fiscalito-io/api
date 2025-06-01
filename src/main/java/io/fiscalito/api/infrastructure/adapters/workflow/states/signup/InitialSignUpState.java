package io.fiscalito.api.infrastructure.adapters.workflow.states.signup;

import lombok.extern.slf4j.Slf4j;
import io.fiscalito.api.application.ports.outbound.client.WhatsappClient;
import io.fiscalito.api.domain.flow.FlowContext;
import io.fiscalito.api.domain.flow.FlowStateEnum;
import io.fiscalito.api.domain.flow.FlowCommand;
import io.fiscalito.api.infrastructure.adapters.workflow.states.BaseState;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import static io.fiscalito.api.domain.flow.FlowStateEnum.INITIAL_SIGN_UP;
import static io.fiscalito.api.domain.flow.FlowStateEnum.SIGN_UP_NAME;

@Slf4j
@Component
public class InitialSignUpState extends BaseState {
    private final WhatsappClient whatsappClient;

    public InitialSignUpState(WhatsappClient whatsappClient, MessageSource messageSource) {
        super(messageSource);
        this.whatsappClient = whatsappClient;
    }

    @Override
    public FlowContext handle(FlowContext context, FlowCommand command) {
        log.debug("Handling initial sign-up state for: {}", command.getFrom());
        context.setCurrentState(SIGN_UP_NAME);
        context.setPreviousState(INITIAL_SIGN_UP);
        whatsappClient.sendMessage(command.getFrom(),
                translateMessage("signup.request.name", null, context.getLocale()));
        context.setWaitingForResponse(true);
        return context;
    }

    @Override
    public FlowStateEnum getFlowState() {
        return INITIAL_SIGN_UP;
    }
} 