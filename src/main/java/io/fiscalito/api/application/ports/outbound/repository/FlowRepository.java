package io.fiscalito.api.application.ports.outbound.repository;

import io.fiscalito.api.domain.flow.FlowContext;

import java.util.Optional;

public interface FlowRepository {
    void save(FlowContext context);
    Optional<FlowContext> findByPhoneNumber(String phoneNumber);
    void delete(String phoneNumber);
} 