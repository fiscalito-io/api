package online.tufactura.api.infrastructure.adapters.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class SoftDeleteBean extends TimestampBean {
    @Column(nullable = false)
    private boolean deleted = false;

    @Column
    private Instant deletedAt;
} 