package io.fiscalito.api.infrastructure.adapters.workflow.states.signup;

import io.fiscalito.api.application.errors.SignUpUserNotFoundException;
import io.fiscalito.api.application.ports.outbound.client.WhatsappClient;
import io.fiscalito.api.application.ports.outbound.repository.SignUpRepository;
import io.fiscalito.api.application.ports.outbound.service.TranslationService;
import io.fiscalito.api.domain.flow.FlowCommand;
import io.fiscalito.api.domain.flow.FlowContext;
import io.fiscalito.api.domain.flow.FlowStateEnum;
import io.fiscalito.api.domain.flow.MessageType;
import io.fiscalito.api.infrastructure.adapters.workflow.states.BaseState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.fiscalito.api.domain.flow.FlowStateEnum.SIGN_UP_EMAIL;
import static io.fiscalito.api.domain.flow.FlowStateEnum.SIGN_UP_NAME;

@Slf4j
@Component
public class NameSignUpState extends BaseState {
    private final WhatsappClient whatsappClient;
    private final SignUpRepository signUpRepository;

    public NameSignUpState(WhatsappClient whatsappClient, TranslationService messageSource, SignUpRepository signUpRepository) {
        super(messageSource);
        this.whatsappClient = whatsappClient;
        this.signUpRepository = signUpRepository;
    }

    @Override
    public FlowContext handle(FlowContext context, FlowCommand command) {
        log.debug("Handling name collection state for: {}", command.getFrom());
        if (command.getType().name().equals(MessageType.TEXT.name())) {
            // Store name in context data
            var persistedUser = this.signUpRepository.findByPhoneNumber(
                    command.getFrom()).orElseThrow(() ->
                    new SignUpUserNotFoundException("User not found for phone number: " + command.getFrom()));
            var name = command.getPayload();
            context.setData(name);
            persistedUser.setName(name);
            context.setCurrentState(SIGN_UP_EMAIL);
            context.setPreviousState(SIGN_UP_NAME);
            this.signUpRepository.save(persistedUser);
            whatsappClient.sendMessage(command.getFrom(),
                    translateMessage("message.signup.request.email",
                            new Object[]{command.getPayload()},
                            context.getLocale()));
        } else {
            whatsappClient.sendMessage(command.getFrom(),
                    "Por favor, envía un mensaje de texto con tu nombre completo.");
        }
        context.setWaitingForResponse(true);
        return context;
    }

    @Override
    public FlowStateEnum getFlowState() {
        return SIGN_UP_NAME;
    }
} 