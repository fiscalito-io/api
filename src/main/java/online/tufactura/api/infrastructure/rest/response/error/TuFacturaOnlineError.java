package online.tufactura.api.infrastructure.rest.response.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TuFacturaOnlineError {
    private String message;
    private String details;
    private String statusCode;
}
