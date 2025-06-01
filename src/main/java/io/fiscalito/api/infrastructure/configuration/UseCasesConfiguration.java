package io.fiscalito.api.infrastructure.configuration;

import io.fiscalito.api.application.ports.inbound.usecase.SignUpFromMessageUseCase;
import io.fiscalito.api.application.ports.outbound.repository.UserRepository;
import io.fiscalito.api.application.ports.outbound.service.EmailService;
import io.fiscalito.api.application.usecases.SignUpFromMessageUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfiguration {

    @Bean
    public SignUpFromMessageUseCase signUpFromMessageUseCase(UserRepository userRepository,
                                                              EmailService emailService) {
        return new SignUpFromMessageUseCaseImpl(userRepository, emailService);
    }

}
