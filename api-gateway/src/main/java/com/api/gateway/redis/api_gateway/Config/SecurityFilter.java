package com.api.gateway.redis.api_gateway.Config;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.api.gateway.redis.api_gateway.Dto.UserSessionDto;
import com.github.benmanes.caffeine.cache.Cache;

import reactor.core.publisher.Mono;

@Component
public class SecurityFilter implements GlobalFilter, Ordered {

    private final WebClient webClient;
    private final Cache<String, UserSessionDto> caffeineCache;

    private static final List<String> PUBLIC_ENDPOINTS = List.of(
        "/auth/login",
        "/auth/register",
        "/auth/validate",
        "/auth/logout"
    );

    public SecurityFilter(WebClient.Builder webClientBuilder, Cache<String, UserSessionDto> caffeineCache) {
        this.webClient = webClientBuilder.baseUrl("http://COOKIES-AUTH").build();
        this.caffeineCache = caffeineCache;
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (isPublicEndpoint(exchange)) {
            return chain.filter(exchange);
        }

        String sessionId = getSessionCookie(exchange);
        if (sessionId == null) { 
            return unauthenticated(exchange);
        }

        UserSessionDto cachedCaffeineCache = caffeineCache.getIfPresent(sessionId);

        if (cachedCaffeineCache != null) {
            ServerWebExchange mutatedExchange = mutateHeaders(exchange, cachedCaffeineCache);
            return chain.filter(mutatedExchange);
        }

        return webClient.get()
                .uri("/auth/validate")
                .cookie("SESSION", sessionId)
                .retrieve()
                .bodyToMono(UserSessionDto.class)
                .flatMap(dto -> {
                    caffeineCache.put(sessionId, dto);
                    // CORRECCIÓN: Capturamos el exchange mutado aquí también antes de pasarlo al chain
                    ServerWebExchange mutatedExchange = mutateHeaders(exchange, dto);
                    return chain.filter(mutatedExchange);
                })
                .onErrorResume(err -> unauthenticated(exchange));
    }

    public String getSessionCookie(ServerWebExchange exchange) {
        var cookie = exchange.getRequest().getCookies().getFirst("SESSION");
        return (cookie != null) ? cookie.getValue() : null;
    }

    public Boolean isPublicEndpoint(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        return PUBLIC_ENDPOINTS.stream().anyMatch(path::startsWith);
    }
    
    public Mono<Void> unauthenticated(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete(); 
    }
    public ServerWebExchange mutateHeaders(ServerWebExchange exchange, UserSessionDto userSession) {
        ServerHttpRequest mutateHttpRequest = exchange.getRequest().mutate()
            .header("X-User-Id", String.valueOf(userSession.getId()))
            .header("X-User-Name", userSession.getUsername())
            .header("X-User-Role", userSession.getRoles())
            .build();
            
        return exchange.mutate().request(mutateHttpRequest).build();
    }
}