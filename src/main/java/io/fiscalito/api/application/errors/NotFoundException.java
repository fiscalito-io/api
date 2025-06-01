package io.fiscalito.api.application.errors;

public class NotFoundException extends FiscalitoException {
    public NotFoundException(String message, String code, String detail) {
        super(message, code, 404, detail);
    }
}
