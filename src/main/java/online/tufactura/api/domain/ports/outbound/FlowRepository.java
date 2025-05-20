package online.tufactura.api.domain.ports.outbound;

import online.tufactura.api.domain.FlowContext;
import java.util.Optional;

public interface FlowRepository {
    void save(FlowContext context);
    Optional<FlowContext> findByPhoneNumber(String phoneNumber);
    void delete(String phoneNumber);
} 