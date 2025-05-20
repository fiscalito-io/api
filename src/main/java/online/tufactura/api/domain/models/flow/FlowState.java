package online.tufactura.api.domain.models.flow;

import online.tufactura.api.domain.models.FlowContext;

public interface FlowState {
    void handle(FlowContext context, FlowCommand command);
    String getStateName();
} 