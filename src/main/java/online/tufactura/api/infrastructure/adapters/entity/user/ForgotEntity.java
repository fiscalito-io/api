package online.tufactura.api.infrastructure.adapters.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import online.tufactura.api.infrastructure.adapters.entity.BaseBean;

import java.time.Instant;

@Entity(name = "forgot")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ForgotEntity extends BaseBean{
    @Column(name = "user_id")
    private String userId;

    @Column(name = "code")
    private String code;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "used")
    private boolean used;

    @Column(name = "used_at")
    private Instant usedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity user;
}
