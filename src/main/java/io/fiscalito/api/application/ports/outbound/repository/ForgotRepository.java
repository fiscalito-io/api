package io.fiscalito.api.application.ports.outbound.repository;

import io.fiscalito.api.domain.user.ForgotModel;

import java.util.Optional;

public interface ForgotRepository {

    Optional<ForgotModel> findById(String id);

    ForgotModel createForgotPasswordToken(ForgotModel id);
}
