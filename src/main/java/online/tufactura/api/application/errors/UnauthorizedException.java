package online.tufactura.api.application.errors;

public class UnauthorizedException extends FacturaOnlineException {
    public UnauthorizedException(String message, String code, String detail) {
        super(message, code, 401, detail);
    }
}
