package io.fiscalito.api.application.ports.outbound.repository;

import io.fiscalito.api.domain.user.UserModel;

import java.util.Optional;

public interface SignUpRepository {
    void save(UserModel user);

    Optional<UserModel> findByPhoneNumber(String email);

    void delete(String phoneNumber);
}
