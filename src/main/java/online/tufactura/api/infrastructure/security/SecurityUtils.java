package online.tufactura.api.infrastructure.security;

import online.tufactura.api.application.errors.InvalidUserTypeException;
import online.tufactura.api.application.errors.UserNotAuthenticatedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public final class SecurityUtils {
    private SecurityUtils(){}

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotAuthenticatedException();
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User)) {
            throw new InvalidUserTypeException();
        }
        return (User) principal;
    }
} 