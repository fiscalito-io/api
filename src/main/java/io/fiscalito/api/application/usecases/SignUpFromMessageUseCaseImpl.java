package io.fiscalito.api.application.usecases;

import io.fiscalito.api.application.errors.UserExistsException;
import io.fiscalito.api.application.ports.inbound.usecase.SignUpFromMessageUseCase;
import io.fiscalito.api.application.ports.outbound.repository.ForgotRepository;
import io.fiscalito.api.application.ports.outbound.repository.UserRepository;
import io.fiscalito.api.application.ports.outbound.service.EmailService;
import io.fiscalito.api.domain.user.ForgotModel;
import io.fiscalito.api.domain.user.UserModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SignUpFromMessageUseCaseImpl implements SignUpFromMessageUseCase {
    private final UserRepository userRepository;
    private final ForgotRepository forgotRepository;
    private final EmailService emailService;

    @Override
    public UserModel signUpFromMessage(UserModel userModel) {
        log.debug("Signing up user from message: {}", userModel);
        var persistedUsers = userRepository.findByEmailOrPhoneNumber(userModel.getEmail(), userModel.getPhoneNumber());
        if (!persistedUsers.isEmpty()) {
            throw new UserExistsException("User already exists with email or phone number: " + userModel.getEmail() + ", " + userModel.getPhoneNumber());
        }
        var persistedUser = this.userRepository.saveUser(userModel);
        log.info("User with email or phone number was created: {}", persistedUser.getEmail());
        var forgotPasswordToken = forgotRepository.createForgotPasswordToken(ForgotModel.builder()
                .userId(persistedUser.getId())
                .build());

        emailService.sendWelcomeEmailSignUpByPhone(userModel.getEmail(), userModel.getName(), forgotPasswordToken);
        return null;
    }

    @Override
    public String getName() {
        return "SignUpFromMessageUseCaseImpl";
    }
}
