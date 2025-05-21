package online.tufactura.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
public class FlowContext implements Serializable {
    private String phoneNumber;
    private String currentState;
    private String previousState;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Instant lastUpdated;
    private String flowType;
    private String data; // JSON string to store any additional data needed by the flow
} 