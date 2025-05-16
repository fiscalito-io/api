package online.tufactura.api.application.ports.inbound.workflow;

public interface Step {
    boolean canHandleState(String state);
}
