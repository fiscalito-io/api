package io.fiscalito.api.application.ports.mapper.user;

import io.fiscalito.api.domain.user.UserModel;
import io.fiscalito.api.infrastructure.adapters.entity.user.UserEntity;

public interface UserMapper {

    UserEntity toEntity(UserModel userModel);

    UserModel toModel(UserEntity userEntity);
}
