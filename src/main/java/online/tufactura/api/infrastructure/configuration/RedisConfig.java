package online.tufactura.api.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import online.tufactura.api.domain.models.FlowContext;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    
    @Bean
    public RedisTemplate<String, FlowContext> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, FlowContext> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
} 