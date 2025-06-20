package io.fiscalito.api.infrastructure.security;

import io.fiscalito.api.application.ports.inbound.PasswordUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtilsImpl implements PasswordUtils {
    @Override
    public String hashPassword(String password) {
        //Hash with BCrypt
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean isPasswordValid(String password, String hashedPassword) {
        //Check if the password matches the hashed password
        return BCrypt.checkpw(password, hashedPassword);
    }
}
