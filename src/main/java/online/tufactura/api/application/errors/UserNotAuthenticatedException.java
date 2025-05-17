package online.tufactura.api.application.errors;

public class UserNotAuthenticatedException extends UnauthorizedException {
    public UserNotAuthenticatedException() {
        super("User not authenticated", "user.not.authenticated", "User is not authenticated");
    }
}
