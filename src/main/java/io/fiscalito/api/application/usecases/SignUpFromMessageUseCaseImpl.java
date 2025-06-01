package io.fiscalito.api.application.usecases;

import io.fiscalito.api.application.ports.inbound.usecase.SignUpFromMessageUseCase;
import io.fiscalito.api.application.ports.outbound.repository.UserRepository;
import io.fiscalito.api.application.ports.outbound.service.EmailService;
import io.fiscalito.api.domain.user.UserModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SignUpFromMessageUseCaseImpl implements SignUpFromMessageUseCase {
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public UserModel signUpFromMessage(UserModel userModel) {
        return null;
    }

    @Override
    public String getName() {
        return "SignUpFromMessageUseCaseImpl";
    }
}
