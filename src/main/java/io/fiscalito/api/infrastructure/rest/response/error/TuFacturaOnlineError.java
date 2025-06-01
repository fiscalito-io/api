package io.fiscalito.api.infrastructure.rest.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TuFacturaOnlineError {
    private String message;
    private String details;
    private String statusCode;
    private String timestamp;
}
