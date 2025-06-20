package io.fiscalito.api.application.usecases;

import io.fiscalito.api.application.ports.outbound.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class UpdatePasswordUseCase {
    private final UserRepository userRepository;


}
