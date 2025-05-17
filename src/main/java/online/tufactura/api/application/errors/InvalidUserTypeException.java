package online.tufactura.api.application.errors;

public class InvalidUserTypeException extends UnauthorizedException {
    public InvalidUserTypeException() {
        super("Invalid user type", "user.invalid.type", "User type is invalid");
    }
}
