package com.auth.statefull.cookies_auth.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityFilterConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                    auth -> auth.requestMatchers(
                    "/auth/register",
                                "/auth/login",
                                "/auth/validate",
                                "/auth/logout"
                    ).permitAll().anyRequest().authenticated()
                ).formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .build();
    }
}
