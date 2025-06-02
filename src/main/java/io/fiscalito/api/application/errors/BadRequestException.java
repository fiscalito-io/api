package io.fiscalito.api.application.errors;

public class BadRequestException extends FiscalitoException {
    public BadRequestException(String message, String code, String detail) {
        super(message, code, 401, detail);
    }
}
