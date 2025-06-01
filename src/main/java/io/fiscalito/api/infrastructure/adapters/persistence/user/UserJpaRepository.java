package io.fiscalito.api.infrastructure.adapters.persistence.user;

import io.fiscalito.api.domain.user.AuthenticationProvider;
import io.fiscalito.api.infrastructure.adapters.entity.user.UserEntity;
import io.fiscalito.api.infrastructure.adapters.persistence.SoftDeleteBeanJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends SoftDeleteBeanJpaRepository<UserEntity> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByProviderAndProviderId(AuthenticationProvider provider, String id);

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
}
