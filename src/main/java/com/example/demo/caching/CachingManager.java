package com.example.demo.caching;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CachingManager extends CachingConfigurerSupport {

    @Value ("${caffeine.cacheExpirationTime}")
    private long cacheExpirationDuration;

    // ref: https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/spring-boot-libraries/src/main/java/com/baeldung/caffeine

    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(cacheExpirationDuration, TimeUnit.SECONDS);
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
//        caffeineCacheManager.getCache("review");
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}
