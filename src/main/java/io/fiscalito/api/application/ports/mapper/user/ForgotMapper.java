package io.fiscalito.api.application.ports.mapper.user;

import io.fiscalito.api.domain.user.ForgotModel;
import io.fiscalito.api.infrastructure.adapters.entity.user.ForgotEntity;

public interface ForgotMapper {
    ForgotEntity toEntity(ForgotModel forgotModel);

    ForgotModel toModel(ForgotEntity forgotEntitys);
}
