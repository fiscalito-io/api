package io.fiscalito.api.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableJpaAuditing
public class JpaConfig {
    @Bean
    public AuditorAware<UUID> auditorProvider() {
        // TODO: Implement real auditor provider that gets the current user ID
        return () -> Optional.of(UUID.randomUUID());
    }
} 