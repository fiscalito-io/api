package io.fiscalito.api.infrastructure.adapters.persistence.user;

import io.fiscalito.api.infrastructure.adapters.entity.user.ForgotEntity;
import io.fiscalito.api.infrastructure.adapters.persistence.BaseBeanJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotJpaRepository extends BaseBeanJpaRepository<ForgotEntity> {
}
