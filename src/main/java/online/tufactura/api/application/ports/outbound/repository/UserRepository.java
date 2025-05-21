package online.tufactura.api.application.ports.outbound.repository;

import online.tufactura.api.domain.AuthenticationProvider;
import online.tufactura.api.domain.UserModel;

import java.util.Optional;

public interface UserRepository {
    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByProviderAndProviderId(AuthenticationProvider provider, String providerId);

    Optional<UserModel> findUserByPhoneNumber(String from);

    UserModel saveUser(UserModel user);
}
