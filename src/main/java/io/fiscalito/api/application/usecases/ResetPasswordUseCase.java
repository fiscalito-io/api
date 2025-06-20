package io.fiscalito.api.application.usecases;

import io.fiscalito.api.application.command.ResetPasswordCommand;
import io.fiscalito.api.application.errors.NotFoundException;
import io.fiscalito.api.application.errors.UserNotFoundException;
import io.fiscalito.api.application.ports.inbound.PasswordUtils;
import io.fiscalito.api.application.ports.outbound.repository.ForgotRepository;
import io.fiscalito.api.application.ports.outbound.repository.UserRepository;
import io.fiscalito.api.application.ports.outbound.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ResetPasswordUseCase {
    private final UserRepository userRepository;
    private final ForgotRepository forgotRepository;
    private final PasswordUtils passwordUtils;
    private final EmailService emailService;

    public void execute(ResetPasswordCommand command) {
        log.info("Executing ResetPasswordUseCase for token: {}", command.getToken());
        var forgot = this.forgotRepository.findById(command.getToken())
                .orElseThrow(() ->
                        new NotFoundException("reset.password.token.not.found",
                                "",
                                "reset.password.token.not.found.detail"));
        if (forgot.isUsed()) {
            throw new NotFoundException("reset.password.token.not.found",
                    "",
                    "reset.password.token.not.found.detail");
        }
        var user = this.userRepository.findById(forgot.getUserId())
                .orElseThrow(UserNotFoundException::new);
        log.info("Resetting password for user: {}", user.getEmail());
        user.setHash(passwordUtils.hashPassword(command.getPassword()));
    }
}
