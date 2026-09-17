package com.api.gateway.redis.api_gateway.Config;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;


@Configuration
public class RedisRateLimiterConfig {
@Bean
public KeyResolver userAddressResolver() {
    return exchange -> {
        var remoteAddress = exchange.getRequest().getRemoteAddress();
        String key = (remoteAddress != null && remoteAddress.getAddress() != null)
            ? remoteAddress.getAddress().getHostAddress()
            : null;
        System.out.println("RATE LIMITER KEY RESUELTA: " + key);
        return Mono.justOrEmpty(key);
    };
}
}
