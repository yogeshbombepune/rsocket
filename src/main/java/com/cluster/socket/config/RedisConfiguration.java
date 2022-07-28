package com.cluster.socket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.cluster.socket.model.DataFrame;

@Configuration
public class RedisConfiguration {

    @Bean
    public ReactiveRedisOperations<String, DataFrame> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<DataFrame> serializer = new Jackson2JsonRedisSerializer<>(DataFrame.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, DataFrame> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, DataFrame> context = 
                builder.value(serializer).hashKey(new StringRedisSerializer()).hashValue(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

}
