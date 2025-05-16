package online.tufactura.api.infrastructure.adapters.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import online.tufactura.api.domain.SoftDeletableBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserEntity extends SoftDeletableBean implements UserDetails {
    @Column(name = "email")
    private String email;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "name")
    private String name;

    @Column(name = "hash")
    private String hash;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "is_locked")
    private boolean isLocked;

    @Column(name = "bad_login_attempts")
    private int badLoginAttempts;

    @Column(name = "last_login_attempt")
    private Instant lastLoginAttempt;

    @Column(name = "last_login_attempt_ip")
    private String lastLoginAttemptIp;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="phone_number_verified")
    private Instant phoneNumberVerified;

    @Column(name="email_verified")
    private Instant emailVerifiedAt;
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.isDeleted();
    }

    @Override
    public boolean isEnabled() {
        return !this.isDeleted();
    }
}
