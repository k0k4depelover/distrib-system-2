package com.websocket.chat.Config;

import java.net.URI;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * AuthHandshakeInterceptor
 */
@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor{

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes){
        
        URI uri =request.getURI();
        String query = uri.getQuery();
        
        if(query==null || !query.startsWith("userId")){
            return false;
        }

        Integer userId= Integer.valueOf(query.replace("userId= ", ""));
        attributes.put("userId", userId);
        return true;
   }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception exception) {
    }

}
