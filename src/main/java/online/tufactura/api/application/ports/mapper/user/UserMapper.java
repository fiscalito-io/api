package online.tufactura.api.application.ports.mapper.user;

import online.tufactura.api.domain.user.UserModel;
import online.tufactura.api.infrastructure.adapters.entity.user.UserEntity;

public interface UserMapper {

    UserEntity toEntity(UserModel userModel);

    UserModel toModel(UserEntity userEntity);
}
