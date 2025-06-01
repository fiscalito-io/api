package io.fiscalito.api.application.ports.inbound.workflow;

import io.fiscalito.api.domain.flow.FlowContext;
import io.fiscalito.api.domain.flow.FlowStateEnum;
import io.fiscalito.api.domain.flow.FlowCommand;

public interface FlowState {
    FlowContext handle(FlowContext context, FlowCommand command);

    FlowStateEnum getFlowState();
} 