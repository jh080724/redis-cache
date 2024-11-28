package com.playdata.rediscache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching  // spring 캐싱 기능 활성화. 외부 저장소와 연계하여 캐시 기능을 사용 설정
public class RedisCacheConfig {
    @Bean
    public CacheManager boardCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration
                = RedisCacheConfiguration.defaultCacheConfig()
                // Redis 에 저장된 데이터 중 Key 의 직렬화 방식을 어떻게 진행할 지 선언.
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new StringRedisSerializer()
                        )
                )
                // Value 의 직렬화 방식 선언 -> JSON 형태로 직렬화 해서 저장.
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                        new Jackson2JsonRedisSerializer<Object>(Object.class)
                ))
                // 데이터의 만료 기간(Time to Live) 설정
                .entryTtl(Duration.ofMinutes(1));// Redis 캐시 보관 시간

        // 스프링의 CacheManager 의 구현체(Redis 용)
        // 접속을 위한 factory, 위에서 설정한 config 정보를 담은 객체를 전달해서 build 후 빈 등록.
        return RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();

    }
}
