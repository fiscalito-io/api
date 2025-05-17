package online.tufactura.api.infrastructure.adapters.persistence.user;

import lombok.RequiredArgsConstructor;
import online.tufactura.api.application.ports.mapper.user.UserMapper;
import online.tufactura.api.application.ports.outbound.repository.UserRepository;
import online.tufactura.api.domain.UserModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImplJpaPersistence implements UserRepository {
    private final UserJpaRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<UserModel> findByEmail(String email) {
        var optUserEntity = this.userRepository.findByEmail(email);
        return optUserEntity.map(userMapper::toModel);
    }

    @Override
    public Optional<UserModel> findByProviderAndProviderId(String provider, String providerId) {
        return Optional.empty();
    }

    @Override
    public UserModel saveUser(UserModel user) {
        return userMapper.toModel(
                this.userRepository.save(userMapper.toEntity(user))
        );
    }
}
