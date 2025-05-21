package online.tufactura.api.infrastructure.adapters.services;

import lombok.extern.slf4j.Slf4j;
import online.tufactura.api.application.ports.inbound.workflow.FlowState;
import online.tufactura.api.domain.FlowContext;
import online.tufactura.api.domain.flow.FlowCommand;
import online.tufactura.api.domain.ports.outbound.FlowRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FlowExecutor {
    private final FlowRepository flowRepository;
    private final Map<String, FlowState> flowStates;

    public FlowExecutor(FlowRepository flowRepository, List<FlowState> flowStates) {
        this.flowRepository = flowRepository;
        this.flowStates = flowStates.stream()
                .collect(Collectors.toMap(FlowState::getStateName, state -> state));
    }

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
        context.setLastUpdated(Instant.now());
        flowRepository.save(context);
    }

    private FlowContext createNewContext(String phoneNumber) {
        log.debug("Creating new flow context for phone number: {}", phoneNumber);
        return FlowContext.builder()
                .phoneNumber(phoneNumber)
                .currentState("INITIAL")
                .lastUpdated(Instant.now())
                .build();
    }
} 