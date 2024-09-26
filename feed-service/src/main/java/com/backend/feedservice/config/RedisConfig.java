package com.backend.feedservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

//    Access Redis from our Spring application
    @Primary
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());  // keys will be stored as simple strings in Redis.
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setHashKeySerializer(new StringRedisSerializer()); // Hash Keys are also stored as simple strings in Redis.
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class)); // hash values will be serialized using Java's default serialization mechanism, allowing you to store complex objects in hashes.
        template.setEnableTransactionSupport(true); // multiple Redis commands can be grouped together into a transaction.
        template.afterPropertiesSet();  // This method performs any necessary initialization or validation tasks.
        return template;
    }

    // establish Redis server Connection
    @Bean
    public LettuceConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("redis-18837.c14.us-east-1-3.ec2.redns.redis-cloud.com");
        configuration.setPort(Integer.parseInt(System.getenv("REDIS_PORT")));
        configuration.setUsername(System.getenv("REDIS_USERNAME"));
        configuration.setPassword(System.getenv("REDIS_PASSWORD"));
        return new LettuceConnectionFactory(configuration);
    }
//
//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//        configuration.setHostName(System.getenv("REDIS_USERNAME"));
//        configuration.setPort(Integer.parseInt(System.getenv("REDIS_PORT")));
//        configuration.setUsername(System.getenv("REDIS_USERNAME"));
//        configuration.setPassword(System.getenv("REDIS_PASSWORD"));
//        return new JedisConnectionFactory(configuration);
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(jedisConnectionFactory());
//        return template;
//    }

}
