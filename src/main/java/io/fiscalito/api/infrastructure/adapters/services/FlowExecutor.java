package io.fiscalito.api.infrastructure.adapters.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import io.fiscalito.api.application.ports.inbound.workflow.FlowState;
import io.fiscalito.api.domain.flow.FlowContext;
import io.fiscalito.api.domain.flow.FlowStateEnum;
import io.fiscalito.api.domain.messages.MessageModel;
import io.fiscalito.api.domain.flow.FlowCommand;
import io.fiscalito.api.application.ports.outbound.repository.FlowRepository;
import io.fiscalito.api.application.ports.outbound.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.fiscalito.api.domain.flow.FlowStateEnum.FINISHED;
import static io.fiscalito.api.domain.flow.FlowStateEnum.FINISHED_ERROR;
import static io.fiscalito.api.domain.flow.FlowStateEnum.INITIAL;

@Slf4j
@Service
public class FlowExecutor {
    private final FlowRepository flowRepository;
    private final MessageRepository messageRepository;
    private final Map<FlowStateEnum, FlowState> flowStates;
    private final ObjectMapper objectMapper;

    public FlowExecutor(FlowRepository flowRepository, MessageRepository messageRepository, List<FlowState> flowStates, ObjectMapper objectMapper) {
        this.flowRepository = flowRepository;
        this.messageRepository = messageRepository;
        this.flowStates = flowStates.stream()
                .collect(Collectors.toMap(FlowState::getFlowState, state -> state));
        this.objectMapper = objectMapper;
    }

    public void execute(FlowCommand command) {
        log.debug("Executing flow command: {} for phone number: {}", command.getType(), command.getFrom());
        var optPersistedMessage =messageRepository.findMessageById(command.getMessageId());
        if(optPersistedMessage.isPresent()) {
            log.debug("Message with id {} already exists, skipping execution.", command.getMessageId());
            return;
        } else {
            try {
                messageRepository.save(MessageModel.builder()
                        .id(command.getMessageId())
                        .phoneNumber(command.getFrom())
                                .provider(command.getMessageProvider())
                        .jsonData(objectMapper.writeValueAsString(command))

                        .build());
            } catch (JsonProcessingException e) {
                //TODO handle error more gracefully
                throw new RuntimeException(e);
            }
        }
        FlowContext context = flowRepository.findByPhoneNumber(command.getFrom())
                .orElseGet(() -> createNewContext(command.getFrom()));
        // if the user sent a message while the flow was waiting for a response, we reset the waiting state
        context.setWaitingForResponse(false);
        while (!Boolean.TRUE.equals(context.getWaitingForResponse()) && !context.getCurrentState().isFinalState()) {
            FlowState currentState = flowStates.get(context.getCurrentState());
            if (currentState == null) {
                log.error("No flow state found for state: {}", context.getCurrentState());
                return;
            }
            currentState.handle(context, command);
            context.setLastUpdated(Instant.now());
            flowRepository.save(context);
        }

        if (context.getCurrentState().equals(FINISHED)) {
            log.info("Flow execution finished for phone number: {}", command.getFrom());
        }
        if (context.getCurrentState().equals(FINISHED_ERROR)) {
            handleError(context, command);
        }
    }

    private void handleError(FlowContext context, FlowCommand command) {
        //TODO implement finalization logic if needed
    }

    private FlowContext createNewContext(String phoneNumber) {
        log.debug("Creating new flow context for phone number: {}", phoneNumber);
        return FlowContext.builder()
                .phoneNumber(phoneNumber)
                .currentState(INITIAL)
                .lastUpdated(Instant.now())
                .waitingForResponse(false)
                .lang("es") // Default language set to Spanish
                .build();
    }
} 