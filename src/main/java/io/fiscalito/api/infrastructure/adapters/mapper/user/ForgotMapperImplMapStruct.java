package io.fiscalito.api.infrastructure.adapters.mapper.user;

import io.fiscalito.api.application.ports.mapper.user.ForgotMapper;
import io.fiscalito.api.domain.user.ForgotModel;
import io.fiscalito.api.infrastructure.adapters.entity.user.ForgotEntity;
import io.fiscalito.api.infrastructure.configuration.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public abstract class ForgotMapperImplMapStruct implements ForgotMapper {

    @Mapping(target = "user.id", source = "userId")
    public abstract ForgotEntity toEntity(ForgotModel forgotModel);

    @Mapping(source = "user.id", target = "userId")
    public abstract ForgotModel toModel(ForgotEntity forgotEntity);
}
