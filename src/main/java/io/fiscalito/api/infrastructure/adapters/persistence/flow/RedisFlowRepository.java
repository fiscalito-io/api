package io.fiscalito.api.infrastructure.adapters.persistence.flow;

import io.fiscalito.api.application.ports.outbound.repository.FlowRepository;
import io.fiscalito.api.domain.flow.FlowContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisFlowRepository implements FlowRepository {
    private static final String KEY_PREFIX = "flow:";
    private static final long EXPIRATION_TIME = 10; // minutes

    private final RedisTemplate<String, FlowContext> redisTemplate;

    @Override
    public void save(FlowContext context) {
        String key = KEY_PREFIX + context.getPhoneNumber();
        log.debug("Saving flow context for phone number: {}", context.getPhoneNumber());
        redisTemplate.opsForValue().set(key, context, EXPIRATION_TIME, TimeUnit.MINUTES);
    }

    @Override
    public Optional<FlowContext> findByPhoneNumber(String phoneNumber) {
        String key = KEY_PREFIX + phoneNumber;
        log.debug("Finding flow context for phone number: {}", phoneNumber);
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    @Override
    public void delete(String phoneNumber) {
        String key = KEY_PREFIX + phoneNumber;
        log.debug("Deleting flow context for phone number: {}", phoneNumber);
        redisTemplate.delete(key);
    }
} 