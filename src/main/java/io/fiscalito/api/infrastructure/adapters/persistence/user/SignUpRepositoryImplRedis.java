package io.fiscalito.api.infrastructure.adapters.persistence.user;

import io.fiscalito.api.application.ports.outbound.repository.SignUpRepository;
import io.fiscalito.api.domain.user.UserModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SignUpRepositoryImplRedis implements SignUpRepository {
    private static final String KEY_PREFIX = "signup:";
    private static final long EXPIRATION_TIME = 2;
    private static final TimeUnit TIME_UNIT = TimeUnit.HOURS;
    private final RedisTemplate<String, UserModel> redisTemplate;

    @Override
    public void save(UserModel user) {
        String key = KEY_PREFIX + user.getPhoneNumber();
        log.debug("Saving signup for phone number: {}", user.getPhoneNumber());
        redisTemplate.opsForValue().set(key, user, EXPIRATION_TIME, TIME_UNIT);
    }

    @Override
    public Optional<UserModel> findByPhoneNumber(String phoneNumber) {
        String key = KEY_PREFIX + phoneNumber;
        log.debug("Finding signup for phone number: {}", phoneNumber);
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    @Override
    public void delete(String phoneNumber) {
        String key = KEY_PREFIX + phoneNumber;
        log.info("Deleting sign up user with phone number: {}", phoneNumber);
        redisTemplate.delete(key);
    }
}
