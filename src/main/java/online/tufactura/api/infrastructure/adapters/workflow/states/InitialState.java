package online.tufactura.api.infrastructure.adapters.workflow.states;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.tufactura.api.application.ports.outbound.client.WhatsappClient;
import online.tufactura.api.application.ports.outbound.repository.UserRepository;
import online.tufactura.api.domain.FlowContext;
import online.tufactura.api.domain.FlowStateEnum;
import online.tufactura.api.domain.MessageType;
import online.tufactura.api.domain.flow.FlowCommand;
import online.tufactura.api.application.ports.inbound.workflow.FlowState;
import org.springframework.stereotype.Component;

import static online.tufactura.api.domain.FlowStateEnum.INITIAL;
import static online.tufactura.api.domain.FlowStateEnum.INITIAL_SIGN_UP;
import static online.tufactura.api.domain.FlowStateEnum.INITIAL_STATE_INVOICING;
import static online.tufactura.api.domain.FlowStateEnum.PROCESSING_AUDIO;

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