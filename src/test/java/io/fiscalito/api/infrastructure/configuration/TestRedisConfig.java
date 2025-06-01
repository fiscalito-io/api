package io.fiscalito.api.infrastructure.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import io.fiscalito.api.domain.flow.FlowContext;

@TestConfiguration
public class TestRedisConfig {
    
    @Bean
    public RedisTemplate<String, FlowContext> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, FlowContext> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
} 