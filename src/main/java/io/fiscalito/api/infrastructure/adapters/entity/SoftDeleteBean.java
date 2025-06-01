package io.fiscalito.api.infrastructure.adapters.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class SoftDeleteBean extends BaseBean {
    @Column(nullable = false)
    private boolean deleted;

    @Column
    private Instant deletedAt;
} 