package io.fiscalito.api.infrastructure.adapters.workflow.states.invoice;

import io.fiscalito.api.application.ports.inbound.workflow.FlowState;
import io.fiscalito.api.domain.flow.FlowCommand;
import io.fiscalito.api.domain.flow.FlowContext;
import io.fiscalito.api.domain.flow.FlowStateEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.fiscalito.api.domain.flow.FlowStateEnum.INITIAL_AUDIO_INVOICE;
import static io.fiscalito.api.domain.flow.FlowStateEnum.INITIAL_TEXT_INVOICE;
import static io.fiscalito.api.domain.messages.MessageType.TEXT;

@Component
@RequiredArgsConstructor
public class InitialAudioInvoicingState implements FlowState {


    @Override
    public FlowContext handle(FlowContext context, FlowCommand command) {
        if (TEXT.equals(command.getType())) {
            context.setCurrentState(INITIAL_TEXT_INVOICE);
        } else {
            context.setCurrentState(INITIAL_AUDIO_INVOICE);
        }
        return context;
    }

    @Override
    public FlowStateEnum getFlowState() {
        return INITIAL_AUDIO_INVOICE;
    }
}
