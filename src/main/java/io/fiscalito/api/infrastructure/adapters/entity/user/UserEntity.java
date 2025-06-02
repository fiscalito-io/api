package io.fiscalito.api.infrastructure.adapters.entity.user;

import io.fiscalito.api.domain.user.AuthenticationProvider;
import io.fiscalito.api.domain.user.UserRole;
import io.fiscalito.api.infrastructure.adapters.entity.SoftDeleteBean;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserEntity extends SoftDeleteBean {
    @Column(name = "email")
    private String email;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private AuthenticationProvider provider;

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

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "phone_number_verified")
    private Instant phoneNumberVerified;

    @Column(name = "email_verified")
    private Instant emailVerifiedAt;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
