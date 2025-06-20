package io.fiscalito.api.infrastructure.rest.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public final class ForgotPasswordRequest {
    @NotNull
    @NotEmpty
    private String email;
}
