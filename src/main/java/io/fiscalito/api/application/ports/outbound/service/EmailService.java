package io.fiscalito.api.application.ports.outbound.service;

import io.fiscalito.api.domain.user.ForgotModel;

public interface EmailService {
    void sendWelcomeEmailSignUpByPhone(String email, String name, ForgotModel forgotPasswordToken);
}
