package online.tufactura.api.application.errors;

public abstract class FacturaOnlineException extends RuntimeException {

    private String message;
    private String code;
    private Integer httpStatusCode;
    private String detail;

    public FacturaOnlineException(String message, String code, Integer httpStatusCode, String detail) {
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

    public String getCode() {
        return code;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getDetail() {
        return detail;
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
