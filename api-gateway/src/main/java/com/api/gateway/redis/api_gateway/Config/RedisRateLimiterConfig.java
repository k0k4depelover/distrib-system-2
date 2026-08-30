package com.api.gateway.redis.api_gateway.Config;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;


@Configuration
public class RedisRateLimiterConfig {
@Bean
public KeyResolver userAddressResolver() {
    return exchange -> Mono.just(
        exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
    );
}
}
