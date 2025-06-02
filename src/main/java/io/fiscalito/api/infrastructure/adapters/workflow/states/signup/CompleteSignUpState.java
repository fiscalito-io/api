package io.fiscalito.api.infrastructure.adapters.workflow.states.signup;

import io.fiscalito.api.application.ports.outbound.client.WhatsappClient;
import io.fiscalito.api.application.ports.outbound.service.TranslationService;
import io.fiscalito.api.domain.flow.FlowCommand;
import io.fiscalito.api.domain.flow.FlowContext;
import io.fiscalito.api.domain.flow.FlowStateEnum;
import io.fiscalito.api.infrastructure.adapters.workflow.states.BaseState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.fiscalito.api.domain.flow.FlowStateEnum.FINISHED;
import static io.fiscalito.api.domain.flow.FlowStateEnum.SIGN_UP_COMPLETE;

@Slf4j
@Component
public class CompleteSignUpState extends BaseState {
    private final WhatsappClient whatsappClient;

    public CompleteSignUpState(TranslationService messageSource, WhatsappClient whatsappClient) {
        super(messageSource);
        this.whatsappClient = whatsappClient;
    }

    @Override
    public FlowContext handle(FlowContext context, FlowCommand command) {
        log.debug("Handling complete sign-up state for: {}", command.getFrom());

        // In this state, we just acknowledge the message and remind about invoice creation
        whatsappClient.sendMessage(command.getFrom(),
                translateMessage("message.signup.complete",
                        null, context.getLocale()));
        context.setCurrentState(FINISHED);
        context.setPreviousState(SIGN_UP_COMPLETE);
        return context;
    }

    @Override
    public FlowStateEnum getFlowState() {
        return SIGN_UP_COMPLETE;
    }
} 