package io.fiscalito.api.application.errors;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("user.not.found", "000000", "The user was not found.");
    }
}
