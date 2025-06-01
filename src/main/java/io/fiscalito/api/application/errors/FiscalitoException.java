package io.fiscalito.api.application.errors;

import lombok.Getter;

public abstract class FiscalitoException extends RuntimeException {

    private final String message;
    @Getter
    private final String code;
    @Getter
    private final Integer httpStatusCode;
    @Getter
    private final String detail;

    public FiscalitoException(String message, String code, Integer httpStatusCode, String detail) {
        super("message: " + message + "detail: " + detail);
        this.message = message;
        this.code = code;
        this.httpStatusCode = httpStatusCode;
        this.detail = detail;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "FacturaOnlineException{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", httpStatusCode=" + httpStatusCode +
                ", detail='" + detail + '\'' +
                '}';
    }
}
