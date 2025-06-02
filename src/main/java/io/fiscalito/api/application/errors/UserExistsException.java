package io.fiscalito.api.application.errors;

public class UserExistsException extends BadRequestException {
    public UserExistsException(String s) {
        super("exception.user.exists.message", "user_exists", s);
    }
}
