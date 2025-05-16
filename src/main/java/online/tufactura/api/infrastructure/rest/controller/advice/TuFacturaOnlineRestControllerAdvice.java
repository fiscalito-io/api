package online.tufactura.api.infrastructure.rest.controller.advice;

import online.tufactura.api.application.errors.FacturaOnlineException;
import online.tufactura.api.infrastructure.rest.response.error.TuFacturaOnlineError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TuFacturaOnlineRestControllerAdvice {

    @ExceptionHandler(FacturaOnlineException.class)
    public ResponseEntity<TuFacturaOnlineError> handleFacturaOnlineException(FacturaOnlineException ex) {
        //TODO use i18n to get the codes.
        return ResponseEntity.status(ex.getHttpStatusCode()).body(
                TuFacturaOnlineError.builder()
                        .message(ex.getMessage())
                        .details(ex.getDetail())
                        .statusCode(ex.getCode())
                        .build()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<TuFacturaOnlineError> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.internalServerError().body(
                TuFacturaOnlineError.builder()
                        .message("Internal Server Error")
                        .details(e.getMessage())
                        .build()
        );
    }
}
