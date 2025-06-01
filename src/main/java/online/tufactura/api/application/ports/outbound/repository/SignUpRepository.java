package online.tufactura.api.application.ports.outbound.repository;

import online.tufactura.api.domain.user.UserModel;

import java.util.Optional;

public interface SignUpRepository {
    void save(UserModel user);

    Optional<UserModel> findByPhoneNumber(String email);

    void delete(String phoneNumber);
}
