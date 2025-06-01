package io.fiscalito.api.infrastructure.adapters.workflow.states.signup;

import io.fiscalito.api.application.ports.outbound.client.WhatsappClient;
import io.fiscalito.api.application.ports.outbound.repository.SignUpRepository;
import io.fiscalito.api.application.ports.outbound.service.TranslationService;
import io.fiscalito.api.domain.flow.FlowCommand;
import io.fiscalito.api.domain.flow.FlowContext;
import io.fiscalito.api.domain.flow.FlowStateEnum;
import io.fiscalito.api.domain.user.AuthenticationProvider;
import io.fiscalito.api.domain.user.UserModel;
import io.fiscalito.api.domain.user.UserRole;
import io.fiscalito.api.infrastructure.adapters.workflow.states.BaseState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static io.fiscalito.api.domain.flow.FlowStateEnum.INITIAL_SIGN_UP;
import static io.fiscalito.api.domain.flow.FlowStateEnum.SIGN_UP_NAME;

@Slf4j
@Component
public class InitialSignUpState extends BaseState {
    private final WhatsappClient whatsappClient;
    private final SignUpRepository signUpRepository;

    public InitialSignUpState(WhatsappClient whatsappClient, TranslationService translationService, SignUpRepository signUpRepository) {
        super(translationService);
        this.whatsappClient = whatsappClient;
        this.signUpRepository = signUpRepository;
    }

    @Override
    public FlowContext handle(FlowContext context, FlowCommand command) {
        log.debug("Handling initial sign-up state for: {}", command.getFrom());
        context.setCurrentState(SIGN_UP_NAME);
        context.setPreviousState(INITIAL_SIGN_UP);
        this.signUpRepository.save(UserModel.builder()
                .phoneNumber(command.getFrom())
                .provider(AuthenticationProvider.PASSWORD)
                .role(UserRole.USER)
                .phoneNumberVerified(Instant.now())
                .isEnabled(true)
                .isVerified(false)
                .isLocked(false)
                .build());
        whatsappClient.sendMessage(command.getFrom(),
                translateMessage("message.signup.request.name", null, context.getLocale()));
        context.setWaitingForResponse(true);
        return context;
    }

    @Override
    public FlowStateEnum getFlowState() {
        return INITIAL_SIGN_UP;
    }
} 