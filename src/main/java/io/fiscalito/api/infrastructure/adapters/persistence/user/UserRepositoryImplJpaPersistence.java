package io.fiscalito.api.infrastructure.adapters.persistence.user;

import io.fiscalito.api.application.ports.mapper.user.UserMapper;
import io.fiscalito.api.application.ports.outbound.repository.UserRepository;
import io.fiscalito.api.domain.user.AuthenticationProvider;
import io.fiscalito.api.domain.user.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public Optional<UserModel> findByProviderAndProviderId(AuthenticationProvider provider, String providerId) {
        var optUserEntity = this.userRepository.findByProviderAndProviderId(provider, providerId);
        return optUserEntity.map(userMapper::toModel);
    }

    @Override
    public Optional<UserModel> findUserByPhoneNumber(String phoneNumber) {
        var optUserEntity = this.userRepository.findByPhoneNumber(phoneNumber);
        return optUserEntity.map(userMapper::toModel);
    }

    @Override
    public UserModel saveUser(UserModel user) {
        return userMapper.toModel(
                this.userRepository.save(userMapper.toEntity(user))
        );
    }

    @Override
    public List<UserModel> findByEmailOrPhoneNumber(String email, String phoneNumber) {
        return userRepository.findByEmailOrPhoneNumber(email, phoneNumber).stream()
                .map(userMapper::toModel)
                .toList();
    }

    @Override
    public Optional<UserModel> findById(String id) {
        var optUserEntity = this.userRepository.findById(id);
        return optUserEntity.map(userMapper::toModel);
    }
}
