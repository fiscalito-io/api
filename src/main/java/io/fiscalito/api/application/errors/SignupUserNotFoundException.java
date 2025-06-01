package io.fiscalito.api.application.errors;

public class SignupUserNotFoundException  extends NotFoundException {
    public SignupUserNotFoundException(String s) {
        super(s, "signup.user.not.found", "The user for the signup process was not found.");
    }
}
