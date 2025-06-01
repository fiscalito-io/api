package io.fiscalito.api.infrastructure.adapters.workflow.states.signup;

import io.fiscalito.api.application.ports.inbound.usecase.SignUpFromMessageUseCase;
import io.fiscalito.api.application.ports.outbound.client.WhatsappClient;
import io.fiscalito.api.application.ports.outbound.repository.SignUpRepository;
import io.fiscalito.api.application.ports.outbound.service.TranslationService;
import io.fiscalito.api.domain.flow.FlowCommand;
import io.fiscalito.api.domain.flow.FlowContext;
import io.fiscalito.api.domain.flow.FlowStateEnum;
import io.fiscalito.api.infrastructure.adapters.workflow.states.BaseState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
        return null;
    }

    @Override
    public FlowStateEnum getFlowState() {
        return null;
    }
}
