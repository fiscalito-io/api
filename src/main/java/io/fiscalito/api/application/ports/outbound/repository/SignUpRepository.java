package io.fiscalito.api.application.ports.outbound.repository;

import io.fiscalito.api.domain.user.UserModel;

import java.util.Optional;

public interface SignUpRepository {
    void save(UserModel user);

    Optional<UserModel> findByPhoneNumber(String phoneNumber);

    void delete(String phoneNumber);
}
