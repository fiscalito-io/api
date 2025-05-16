package online.tufactura.api.application.errors;

public class NotFoundException extends FacturaOnlineException {
    public NotFoundException(String message, String code, String detail) {
        super(message, code, 404, detail);
    }
}
