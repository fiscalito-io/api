package online.tufactura.api.infrastructure.adapters.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.tufactura.api.domain.FlowContext;
import online.tufactura.api.domain.ports.outbound.FlowRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisFlowRepository implements FlowRepository {
    private static final String KEY_PREFIX = "flow:";
    private static final long EXPIRATION_TIME = 24; // hours

    private final RedisTemplate<String, FlowContext> redisTemplate;

    @Override
    public void save(FlowContext context) {
        String key = KEY_PREFIX + context.getPhoneNumber();
        log.debug("Saving flow context for phone number: {}", context.getPhoneNumber());
        redisTemplate.opsForValue().set(key, context, EXPIRATION_TIME, TimeUnit.HOURS);
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