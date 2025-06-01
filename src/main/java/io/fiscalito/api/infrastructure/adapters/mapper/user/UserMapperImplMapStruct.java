package io.fiscalito.api.infrastructure.adapters.mapper.user;

import io.fiscalito.api.application.ports.mapper.user.UserMapper;
import io.fiscalito.api.domain.user.UserModel;
import io.fiscalito.api.infrastructure.adapters.entity.user.UserEntity;
import io.fiscalito.api.infrastructure.configuration.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public abstract class UserMapperImplMapStruct implements UserMapper {
    @Mapping(target = "role", source = "role")
    public abstract UserEntity toEntity(UserModel userModel);

    @Mapping(target = "role", source = "role")
    public abstract UserModel toModel(UserEntity userEntity);
}
