package online.tufactura.api.application.ports.outbound.repository;

import online.tufactura.api.domain.UserModel;

import java.util.Optional;

public interface UserRepository {
    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByProviderAndProviderId(String provider, String providerId);

    UserModel saveUser(UserModel user);
}
