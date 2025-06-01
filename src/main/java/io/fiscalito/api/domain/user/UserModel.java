package io.fiscalito.api.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import io.fiscalito.api.domain.SoftDeleteModel;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserModel extends SoftDeleteModel {
    private String email;
    private AuthenticationProvider provider;
    private String providerId;
    private String name;
    private String hash;
    private boolean isEnabled;
    private boolean isVerified;
    private boolean isLocked;
    private int badLoginAttempts;
    private Instant lastLoginAttempt;
    private String lastLoginAttemptIp;
    private String phoneNumber;
    private Instant phoneNumberVerified;
    private Instant emailVerifiedAt;
    private UserRole role;
}
