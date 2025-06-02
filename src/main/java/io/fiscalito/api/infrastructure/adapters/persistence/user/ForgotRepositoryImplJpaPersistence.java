package io.fiscalito.api.infrastructure.adapters.persistence.user;

import io.fiscalito.api.application.ports.mapper.user.ForgotMapper;
import io.fiscalito.api.application.ports.outbound.repository.ForgotRepository;
import io.fiscalito.api.domain.user.ForgotModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ForgotRepositoryImplJpaPersistence implements ForgotRepository {
    private final ForgotJpaRepository forgotJpaRepository;
    private final ForgotMapper forgotMapper;

    @Override
    public Optional<ForgotModel> findById(String id) {
        return forgotJpaRepository.findById(id)
                .map(forgotMapper::toModel);
    }

    @Override
    public ForgotModel createForgotPasswordToken(ForgotModel forgotModel) {
        return forgotMapper.toModel(forgotJpaRepository.save(forgotMapper.toEntity(forgotModel)));
    }
}
