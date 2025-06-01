package io.fiscalito.api.application.errors;

public class SignUpUserNotFoundException extends NotFoundException {
    public SignUpUserNotFoundException(String s) {
        super(s, "signup.user.not.found", "The user for the signup process was not found.");
    }
}
