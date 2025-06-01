package io.fiscalito.api.application.ports.inbound.usecase;

import io.fiscalito.api.domain.user.UserModel;

public interface SignUpFromMessageUseCase extends BaseUseCase{
    public UserModel signUpFromMessage(UserModel userModel);
}
