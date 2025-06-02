package io.fiscalito.api.infrastructure.adapters.workflow.states;

import io.fiscalito.api.application.ports.inbound.workflow.FlowState;
import io.fiscalito.api.application.ports.outbound.client.WhatsappClient;
import io.fiscalito.api.application.ports.outbound.repository.UserRepository;
import io.fiscalito.api.domain.flow.FlowCommand;
import io.fiscalito.api.domain.flow.FlowContext;
import io.fiscalito.api.domain.flow.FlowStateEnum;
import io.fiscalito.api.domain.messages.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.fiscalito.api.domain.flow.FlowStateEnum.INITIAL;
import static io.fiscalito.api.domain.flow.FlowStateEnum.INITIAL_SIGN_UP;
import static io.fiscalito.api.domain.flow.FlowStateEnum.INITIAL_STATE_INVOICING;
import static io.fiscalito.api.domain.flow.FlowStateEnum.PROCESSING_AUDIO;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialState implements FlowState {
    private final WhatsappClient whatsappClient;
    private final UserRepository userRepository;

    @Override
    public FlowContext handle(FlowContext context, FlowCommand command) {
        log.debug("Handling command in InitialState for phone number: {}", command.getFrom());
        userRepository.findUserByPhoneNumber(command.getFrom())
                .ifPresentOrElse(user -> {
                    log.info("User found: {}", user);
                    context.setCurrentState(INITIAL_STATE_INVOICING);
                }, () -> {
                    log.warn("User not found for phone number: {}", command.getFrom());
                    context.setCurrentState(INITIAL_SIGN_UP);
                });
        if (MessageType.AUDIO.equals(command.getType())) {
            // Process audio message
            context.setCurrentState(PROCESSING_AUDIO);
            context.setPreviousState(INITIAL);
            whatsappClient.sendMessage(command.getFrom(), "Estamos procesando tu mensaje de audio...");
        }
        return context;
    }

    @Override
    public FlowStateEnum getFlowState() {
        return INITIAL;
    }
} 