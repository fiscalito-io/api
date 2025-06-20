package io.fiscalito.api.infrastructure.configuration;

import io.fiscalito.api.application.ports.inbound.PasswordUtils;
import io.fiscalito.api.application.ports.inbound.usecase.SignUpFromMessageUseCase;
import io.fiscalito.api.application.ports.outbound.repository.ForgotRepository;
import io.fiscalito.api.application.ports.outbound.repository.UserRepository;
import io.fiscalito.api.application.ports.outbound.service.EmailService;
import io.fiscalito.api.application.usecases.ForgotPasswordUseCase;
import io.fiscalito.api.application.usecases.ResetPasswordUseCase;
import io.fiscalito.api.application.usecases.SignUpFromMessageUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfiguration {
    @Bean
    public SignUpFromMessageUseCase signUpFromMessageUseCase(UserRepository userRepository,
                                                             ForgotRepository forgotRepository,
                                                             EmailService emailService) {
        return new SignUpFromMessageUseCaseImpl(userRepository, forgotRepository, emailService);
    }

    @Bean
    public ForgotPasswordUseCase forgotPasswordUseCase(UserRepository userRepository,
                                                       ForgotRepository forgotRepository,
                                                       EmailService emailService) {
        return new ForgotPasswordUseCase(userRepository, forgotRepository, emailService);
    }

    @Bean
    public ResetPasswordUseCase resetPasswordUseCase(UserRepository userRepository,
                                                     ForgotRepository forgotRepository,
                                                     PasswordUtils passwordUtils,
                                                     EmailService emailService) {
        return new ResetPasswordUseCase(userRepository, forgotRepository, passwordUtils, emailService);
    }

}
