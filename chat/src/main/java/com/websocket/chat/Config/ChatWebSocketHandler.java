package com.websocket.chat.Config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * ChatWebSocketHandler
 */
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler{
    private final SessionRegistry sessionRegistry;
    private final MessageRouter messageRouter;
    
    private ChatWebSocketHandler(SessionRegistry sessionRegistry, 
        MessageRouter messageRouter
    ){
        this.messageRouter= messageRouter;
        this.sessionRegistry= sessionRegistry;
    }

    public void afterConnectionEstablished(WebSocketSession session){
        Integer userId = (Integer) session.getAttributes().get("userId");

        sessionRegistry.register(userId, session);

        System.out.println("USER CONNECTED: "+ userId);

    }

    protected void handleTextMessage(WebSocketSession session, TextMessage message)throws IOException{
        Integer userId = (Integer) session.getAttributes().get("userId");

        messageRouter.route(userId, session, message.getPayload());
    }

    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        Integer userId = (Integer) session.getAttributes().get("userId");

        sessionRegistry.remove(userId);
        System.out.println("USER DISCONNECTED: "+  userId);
    }

    public void handleTransportError(WebSocketSession session, Throwable exception){
        Integer userId = (Integer) session.getAttributes().get("userId");

        sessionRegistry.remove(userId);

        System.out.println("TRANSPORT ERROR FOR USER: "+ userId);

    }
}
