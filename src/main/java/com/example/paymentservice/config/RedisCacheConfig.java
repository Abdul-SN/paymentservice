package com.example.paymentservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@Configuration
public class RedisCacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        // дефолтная конфигурация: TTL = 60 сек, null-значения не кэшируются
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(60))
                .disableCachingNullValues();

        // можно задать специфичные настройки для конкретного кэша
        RedisCacheConfiguration currencyConfig = defaultConfig;

        return RedisCacheManager.builder(factory)
                // по умолчанию для всех кэшей
                .cacheDefaults(defaultConfig)
                // специальная конфигурация для «currencyRates»
                .withCacheConfiguration("currencyRates", currencyConfig)
                .transactionAware()
                .build();
    }
}
