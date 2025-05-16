package online.tufactura.api.infrastructure.adapters.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import online.tufactura.api.infrastructure.adapters.entity.BaseBean;

@Entity(name = "refresh_token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class RefreshToken extends BaseBean {
    @Column(name = "device_id")
    private String deviceId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "token")
    private String token;
    @Column(name = "expires_at")
    private Long expiresAt;
    @Column(name = "revoked")
    private Boolean revoked;
    @Column(name = "revoked_at")
    private Long revokedAt;



}
