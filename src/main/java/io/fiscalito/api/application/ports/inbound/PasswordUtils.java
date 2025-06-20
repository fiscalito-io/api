package io.fiscalito.api.application.ports.inbound;

public interface PasswordUtils {
    String hashPassword(String password);

    boolean isPasswordValid(String password, String hashedPassword);
}
