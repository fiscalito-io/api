package online.tufactura.api.domain;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class FlowContext implements Serializable {
    private String phoneNumber;
    private String currentState;
    private String previousState;
    private LocalDateTime lastUpdated;
    private String flowType;
    private String data; // JSON string to store any additional data needed by the flow
} 