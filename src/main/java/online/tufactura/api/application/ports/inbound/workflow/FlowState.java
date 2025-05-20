package online.tufactura.api.application.ports.inbound.workflow;

import online.tufactura.api.domain.FlowContext;
import online.tufactura.api.domain.flow.FlowCommand;

public interface FlowState {
    void handle(FlowContext context, FlowCommand command);
    String getStateName();
} 