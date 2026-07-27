package com.api.gateway.redis.api_gateway.Config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.api.gateway.redis.api_gateway.Dto.UserSessionDto;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;



@Configuration
public class CacheCaffeine {
    
    @Bean
    public Cache<String, UserSessionDto> caffeineCache(){
        Caffeine.newBuilder();
        return Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(10000)
                .build();
    }
}
