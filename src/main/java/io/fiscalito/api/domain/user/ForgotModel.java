package io.fiscalito.api.domain.user;

import io.fiscalito.api.domain.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class ForgotModel extends BaseModel {
    private Instant expiresAt;
    private boolean used;
    private Instant usedAt;
    private String userId;
}
