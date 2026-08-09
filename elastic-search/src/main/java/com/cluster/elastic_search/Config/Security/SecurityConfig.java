package com.cluster.elastic_search.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserContextFilter UserContextFilter) throws Exception{
        http
            .csrf(csrf -> csrf.disable())
            .addFilterBefore(UserContextFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth ->
                auth.requestMatchers("/api/imagenes/**").authenticated()
                .anyRequest().permitAll()
            );
        return http.build();
    }    
}
