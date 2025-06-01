package io.fiscalito.api.application.errors;

public class UnauthorizedException extends FiscalitoException {
    public UnauthorizedException(String message, String code, String detail) {
        super(message, code, 401, detail);
    }
}
