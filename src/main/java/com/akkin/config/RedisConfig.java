package com.akkin.config;

import com.akkin.login.dto.AuthMember;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, AuthMember> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, AuthMember> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        Jackson2JsonRedisSerializer<AuthMember> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
            AuthMember.class);
        ObjectMapper objectMapper = new ObjectMapper().enableDefaultTyping(
            ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setKeySerializer(new StringRedisSerializer());

        return template;
    }
}
