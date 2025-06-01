package io.fiscalito.api.infrastructure.rest.controller.advice;

import io.fiscalito.api.application.errors.FiscalitoException;
import io.fiscalito.api.infrastructure.rest.response.error.TuFacturaOnlineError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TuFacturaOnlineRestControllerAdvice {

    @ExceptionHandler(FiscalitoException.class)
    public ResponseEntity<TuFacturaOnlineError> handleFacturaOnlineException(FiscalitoException ex) {
        //TODO use i18n to get the codes.
        return ResponseEntity.status(ex.getHttpStatusCode()).body(
                TuFacturaOnlineError.builder()
                        .message(ex.getMessage())
                        .details(ex.getDetail())
                        .statusCode(ex.getCode())
                        .build()
        );
    }

//    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
//    public ResponseEntity<TuFacturaOnlineError> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
//                .body(TuFacturaOnlineError.builder()
//                        .message("Media type not acceptable")
//                        .details(ex.getMessage())
//                        .statusCode(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
//                        .build());
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<TuFacturaOnlineError> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(TuFacturaOnlineError.builder()
                        .message("Internal Server Error")
                        .details(e.getMessage())
                        .statusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .build());
    }
}
