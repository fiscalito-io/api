package online.tufactura.api.infrastructure.adapters.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.tufactura.api.domain.MessageModel;
import online.tufactura.api.domain.ports.outbound.MessageRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisMessageRepository implements MessageRepository {
    private static final String KEY_PREFIX = "message:";
    private static final long EXPIRATION_TIME = 10; // hours
    private final RedisTemplate<String, MessageModel> redisTemplate;

    @Override
    public void save(MessageModel message) {
        String key = KEY_PREFIX + message.getId();
        log.debug("Saving message with id: {}", message.getId());
        redisTemplate.opsForValue().set(key, message, EXPIRATION_TIME, TimeUnit.MINUTES);
    }

    @Override
    public Optional<MessageModel> findMessageById(String messageId) {
        String key = KEY_PREFIX + messageId;
        log.debug("Finding message with id: {}", messageId);
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    @Override
    public void delete(String messageId) {
        String key = KEY_PREFIX + messageId;
        log.debug("Deleting message with id: {}", messageId);
        redisTemplate.delete(key);
    }
}
