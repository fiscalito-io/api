package online.tufactura.api.infrastructure.adapters.persistence.user;

import online.tufactura.api.infrastructure.adapters.entity.user.UserEntity;
import online.tufactura.api.infrastructure.adapters.persistence.SoftDeleteBeanJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends SoftDeleteBeanJpaRepository<UserEntity> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
}
