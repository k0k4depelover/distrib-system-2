package com.cluster.elastic_search.Config.Security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserContextFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            
        String userIdHeader = request.getHeader("X-User-Id");
        String userRolesHeader = request.getHeader("X-User-Role");
        
        /*
        Si no llega el header no se autentica a nadie.
        El endpoint ademas es protegido por security config, esto del lado del API gateway.
        */

        if(userIdHeader != null && !userIdHeader.isBlank()){

            try{

                Long userId = Long.valueOf(userIdHeader);

                List<SimpleGrantedAuthority> authorities = (userRolesHeader != null && !userRolesHeader.isBlank())
                    ? Arrays.stream(userRolesHeader.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .toList()
                    : List.of();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            catch (NumberFormatException e){

            }

        }

        filterChain.doFilter(request, response);

        }
    
}
