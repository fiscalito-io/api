package io.fiscalito.api.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import io.fiscalito.api.domain.flow.FlowContext;
import io.fiscalito.api.domain.messages.MessageModel;
import io.fiscalito.api.domain.user.UserModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    
    @Bean
    public RedisTemplate<String, FlowContext> flowRedisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        RedisTemplate<String, FlowContext> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<FlowContext> valueSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, FlowContext.class);
        template.setValueSerializer(valueSerializer);
        template.afterPropertiesSet();
        return template;
    }


    @Bean
    public RedisTemplate<String, MessageModel> messageRedisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        RedisTemplate<String, MessageModel> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<MessageModel> valueSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, MessageModel.class);
        template.setValueSerializer(valueSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisTemplate<String, UserModel> signupRedisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        RedisTemplate<String, UserModel> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<UserModel> valueSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, UserModel.class);
        template.setValueSerializer(valueSerializer);
        template.afterPropertiesSet();
        return template;
    }
} 