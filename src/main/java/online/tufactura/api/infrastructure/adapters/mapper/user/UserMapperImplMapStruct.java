package online.tufactura.api.infrastructure.adapters.mapper.user;

import online.tufactura.api.application.ports.mapper.user.UserMapper;
import online.tufactura.api.domain.UserModel;
import online.tufactura.api.infrastructure.adapters.entity.user.UserEntity;
import online.tufactura.api.infrastructure.configuration.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public abstract class UserMapperImplMapStruct implements UserMapper {
    @Mapping(target = "role", source = "role")
    public abstract UserEntity toEntity(UserModel userModel);

    @Mapping(target = "role", source = "role")
    public abstract UserModel toModel(UserEntity userEntity);
}
