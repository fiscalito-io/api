package io.fiscalito.api.application.usecases;

import io.fiscalito.api.application.command.ForgotPasswordCommand;
import io.fiscalito.api.application.errors.UserNotFoundException;
import io.fiscalito.api.application.ports.outbound.repository.ForgotRepository;
import io.fiscalito.api.application.ports.outbound.repository.UserRepository;
import io.fiscalito.api.application.ports.outbound.service.EmailService;
import io.fiscalito.api.domain.user.ForgotModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordUseCase {

    private final UserRepository userRepository;
    private final ForgotRepository forgotRepository;
    private final EmailService emailService;

    public void execute(final ForgotPasswordCommand forgotPasswordCommand) {
        var user = userRepository.findByEmail(forgotPasswordCommand.email()).orElseThrow(UserNotFoundException::new);
        var forgot = forgotRepository.createForgotPasswordToken(ForgotModel.builder()
                .userId(user.getId())
                .used(false)
                .build());
        emailService.sendForgotPasswordEmail(forgotPasswordCommand.email(), forgot);
    }
}
