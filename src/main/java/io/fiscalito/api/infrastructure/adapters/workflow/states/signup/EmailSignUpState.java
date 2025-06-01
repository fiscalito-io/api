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

import java.util.regex.Pattern;

import static io.fiscalito.api.domain.flow.FlowStateEnum.SIGN_UP_COMPANY_NAME;
import static io.fiscalito.api.domain.flow.FlowStateEnum.SIGN_UP_EMAIL;

@Slf4j
@Component
public class EmailSignUpState extends BaseState {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private final WhatsappClient whatsappClient;
    private final SignUpRepository signUpRepository;

    public EmailSignUpState(WhatsappClient whatsappClient, TranslationService translationService, SignUpRepository signUpRepository) {
        super(translationService);
        this.whatsappClient = whatsappClient;
        this.signUpRepository = signUpRepository;
    }

    @Override
    public FlowContext handle(FlowContext context, FlowCommand command) {
        log.debug("Handling email collection state for: {}", command.getFrom());
        if (MessageType.TEXT.name().equals(command.getType().name())) {
            String email = command.getPayload();
            var persistedUser = signUpRepository.findByPhoneNumber(command.getFrom()).orElseThrow(() -> new SignUpUserNotFoundException(command.getFrom()));
            if (isValidEmail(email)) {
                // Store email in context data
                persistedUser.setEmail(email);
                context.setData(email);
                context.setCurrentState(SIGN_UP_COMPANY_NAME);
                context.setPreviousState(SIGN_UP_EMAIL);
                signUpRepository.save(persistedUser);
                whatsappClient.sendMessage(command.getFrom(),
                        translateMessage("message.signup.request.company.name",
                                new Object[]{persistedUser.getName()},
                                context.getLocale()));
            } else {
                whatsappClient.sendMessage(command.getFrom(),
                        translateMessage("message.error.invalid.email",
                                new Object[]{email},
                                context.getLocale()));
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