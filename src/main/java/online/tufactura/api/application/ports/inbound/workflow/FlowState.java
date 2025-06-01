package online.tufactura.api.application.ports.inbound.workflow;

import online.tufactura.api.domain.FlowContext;
import online.tufactura.api.domain.FlowStateEnum;
import online.tufactura.api.domain.flow.FlowCommand;

public interface FlowState {
    FlowContext handle(FlowContext context, FlowCommand command);

    FlowStateEnum getFlowState();
} 