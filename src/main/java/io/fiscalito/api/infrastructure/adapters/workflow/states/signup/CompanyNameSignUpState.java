package io.fiscalito.api.infrastructure.adapters.workflow.states.signup;

import io.fiscalito.api.application.errors.SignUpUserNotFoundException;
import io.fiscalito.api.application.ports.inbound.usecase.SignUpFromMessageUseCase;
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

import static io.fiscalito.api.domain.flow.FlowStateEnum.SIGN_UP_COMPANY_NAME;
import static io.fiscalito.api.domain.flow.FlowStateEnum.SIGN_UP_COMPLETE;

@Component
@Slf4j
public class CompanyNameSignUpState extends BaseState {
    private final WhatsappClient whatsappClient;
    private final SignUpRepository signUpRepository;
    private final SignUpFromMessageUseCase useCase;

    public CompanyNameSignUpState(TranslationService messageSource, WhatsappClient whatsappClient, SignUpRepository signUpRepository, SignUpFromMessageUseCase useCase) {
        super(messageSource);
        this.whatsappClient = whatsappClient;
        this.signUpRepository = signUpRepository;
        this.useCase = useCase;
    }

    @Override
    public FlowContext handle(FlowContext context, FlowCommand command) {
        log.debug("Handling name collection state for: {}", command.getFrom());
        if (command.getType().name().equals(MessageType.TEXT.name())) {
            var persistedUser = this.signUpRepository.findByPhoneNumber(
                    command.getFrom()).orElseThrow(() ->
                    new SignUpUserNotFoundException("User not found for phone number: " + command.getFrom()));
            var companyName = command.getPayload();
            persistedUser.setCompanyName(companyName);
            this.signUpRepository.save(persistedUser);
            this.useCase.signUpFromMessage(persistedUser);
            whatsappClient.sendMessage(command.getFrom(),
                    translateMessage("message.signup.request.processing.request",
                            new Object[]{companyName},
                            context.getLocale()));
            context.setCurrentState(SIGN_UP_COMPLETE);
            context.setPreviousState(SIGN_UP_COMPANY_NAME);
            context.setWaitingForResponse(true);
        } else {
            whatsappClient.sendMessage(command.getFrom(),
                    "Por favor, envía un mensaje de texto con tu nombre completo.");
        }
        return context;
    }

    @Override
    public FlowStateEnum getFlowState() {
        return SIGN_UP_COMPANY_NAME;
    }
}
