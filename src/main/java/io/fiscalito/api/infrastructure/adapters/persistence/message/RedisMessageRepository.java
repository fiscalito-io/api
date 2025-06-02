package io.fiscalito.api.infrastructure.adapters.persistence.message;

import io.fiscalito.api.application.ports.outbound.repository.MessageRepository;
import io.fiscalito.api.domain.messages.MessageModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisMessageRepository implements MessageRepository {
    private static final String KEY_PREFIX = "message:";
    private static final long EXPIRATION_TIME = 10;
    private static final TimeUnit TIME_UNIT = TimeUnit.MINUTES;
    private final RedisTemplate<String, MessageModel> redisTemplate;

    @Override
    public void save(MessageModel message) {
        String key = KEY_PREFIX + message.getId();
        log.debug("Saving message with id: {}", message.getId());
        redisTemplate.opsForValue().set(key, message, EXPIRATION_TIME, TIME_UNIT);
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
