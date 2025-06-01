package online.tufactura.api.application.ports.outbound.repository;

import online.tufactura.api.domain.flow.FlowContext;
import java.util.Optional;

public interface FlowRepository {
    void save(FlowContext context);
    Optional<FlowContext> findByPhoneNumber(String phoneNumber);
    void delete(String phoneNumber);
} 