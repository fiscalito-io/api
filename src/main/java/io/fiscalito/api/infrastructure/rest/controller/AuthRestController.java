package io.fiscalito.api.infrastructure.rest.controller;

import io.fiscalito.api.application.command.ForgotPasswordCommand;
import io.fiscalito.api.application.usecases.ForgotPasswordUseCase;
import io.fiscalito.api.application.usecases.ResetPasswordUseCase;
import io.fiscalito.api.application.usecases.UpdatePasswordUseCase;
import io.fiscalito.api.infrastructure.rest.request.ForgotPasswordRequest;
import io.fiscalito.api.infrastructure.rest.request.ResetPasswordRequest;
import io.fiscalito.api.infrastructure.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.fiscalito.api.infrastructure.utils.ApiConstants.AUTH_BASE_PATH;
import static io.fiscalito.api.infrastructure.utils.ApiConstants.FORGOT_PASSWORD;
import static io.fiscalito.api.infrastructure.utils.ApiConstants.ME;
import static io.fiscalito.api.infrastructure.utils.ApiConstants.RESET_PASSWORD;

@RestController
@RequestMapping(AUTH_BASE_PATH)
@RequiredArgsConstructor
public class AuthRestController {

    private final ForgotPasswordUseCase forgotPasswordUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;
    private final UpdatePasswordUseCase updatePasswordUseCase;

    @GetMapping(ME)
    public Object getCurrentUser() {
        return SecurityUtils.getCurrentUser();
    }

    @PostMapping(FORGOT_PASSWORD)
    public void ForgotPassword(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest) {
        this.forgotPasswordUseCase.execute(new ForgotPasswordCommand(forgotPasswordRequest.getEmail()));
    }

    @PostMapping(RESET_PASSWORD)
    public void resetPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
        // Logic to handle password reset
        // This could involve validating the reset token and updating the user's password.
    }

}
