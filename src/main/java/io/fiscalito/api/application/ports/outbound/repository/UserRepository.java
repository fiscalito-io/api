package io.fiscalito.api.application.ports.outbound.repository;

import io.fiscalito.api.domain.user.AuthenticationProvider;
import io.fiscalito.api.domain.user.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByProviderAndProviderId(AuthenticationProvider provider, String providerId);

    Optional<UserModel> findUserByPhoneNumber(String from);

    UserModel saveUser(UserModel user);

    List<UserModel> findByEmailOrPhoneNumber(String email, String phoneNumber);

    Optional<UserModel> findById(String email);
}
