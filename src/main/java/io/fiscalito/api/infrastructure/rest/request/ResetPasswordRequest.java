package io.fiscalito.api.infrastructure.rest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class ResetPasswordRequest {
    private String token;
    private String password;
    private String confirmPassword;
}
