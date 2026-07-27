package com.websocket.chat.Config;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * SessionRegistry
 */

@Component
public class SessionRegistry {
    private final Map<Integer, WebSocketSession> sessionByUserId = new ConcurrentHashMap<>();

    public void register(Integer userId, WebSocketSession session) {
        sessionByUserId.put(userId, session);
    }

    public void remove(Integer userId){
        sessionByUserId.remove(userId);
    }

    public Optional<WebSocketSession> getSession(Integer userId){
        return Optional.ofNullable(sessionByUserId.get(userId));
    }

    public boolean isOnline(Integer userId){
        WebSocketSession session = sessionByUserId.get(userId);
        return session != null && session.isOpen();
    }

    public void sendToUser(Integer userId, String jsonMessage) throws IOException{

        WebSocketSession session = sessionByUserId.get(userId);

        if(session == null || session.isOpen()){
            throw new IllegalStateException("USER OFFLINE");
        }
        session.sendMessage(new TextMessage(jsonMessage));

    }

}
