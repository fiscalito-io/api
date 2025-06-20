package io.fiscalito.api.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResetPasswordCommand {
    private String token;
    private String password;
    private String confirmPassword;
}
