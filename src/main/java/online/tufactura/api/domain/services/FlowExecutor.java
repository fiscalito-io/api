package online.tufactura.api.domain.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.tufactura.api.domain.models.FlowContext;
import online.tufactura.api.domain.models.flow.FlowCommand;
import online.tufactura.api.domain.models.flow.FlowState;
import online.tufactura.api.domain.ports.outbound.FlowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlowExecutor {
    private final FlowRepository flowRepository;
    private final Map<String, FlowState> flowStates;

    public void execute(FlowCommand command) {
        log.debug("Executing flow command: {} for phone number: {}", command.getType(), command.getFrom());
        
        FlowContext context = flowRepository.findByPhoneNumber(command.getFrom())
                .orElseGet(() -> createNewContext(command.getFrom()));

        FlowState currentState = flowStates.get(context.getCurrentState());
        if (currentState == null) {
            log.error("No flow state found for state: {}", context.getCurrentState());
            return;
        }

        currentState.handle(context, command);
        context.setLastUpdated(LocalDateTime.now());
        flowRepository.save(context);
    }

    private FlowContext createNewContext(String phoneNumber) {
        log.debug("Creating new flow context for phone number: {}", phoneNumber);
        return FlowContext.builder()
                .phoneNumber(phoneNumber)
                .currentState("INITIAL")
                .lastUpdated(LocalDateTime.now())
                .build();
    }
} 