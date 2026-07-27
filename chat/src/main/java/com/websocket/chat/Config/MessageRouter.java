package com.websocket.chat.Config;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.chat.Dto.PrivateMessageSendPayload;
import com.websocket.chat.Dto.WsEnvelope;

/**
 * MessageRouter
 */
@Component
public class MessageRouter {
    private final ObjectMapper objectMapper;
    private final PrivateMessageHandler privateMessageHandler;

    public MessageRouter(ObjectMapper objectMapper, PrivateMessageHandler privateMessageHandler) {
        this.objectMapper = objectMapper;
        this.privateMessageHandler = privateMessageHandler;
    }

    public void route(Integer senderUserId, WebSocketSession session, String rawJson) throws IOException{
        WsEnvelope<?> envelope = objectMapper.readValue(rawJson, WsEnvelope.class);
        
        switch (envelope.getType()) {
            case "private.message.send" ->{

                 PrivateMessageSendPayload payload = objectMapper.convertValue(
                    envelope.getPayload(), 
                    PrivateMessageSendPayload.class
                );
                privateMessageHandler.handle(
                        senderUserId, 
                        envelope.getRequestId(),
                        payload
                    

                );
            }
            case "ping" ->{
                WsEnvelope<Object> pong = new WsEnvelope<>(
                    "pong",
                    envelope.getRequestId(),
                    Map.of("status", "ok")
                );

                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(pong)));
            }
        
            default -> {
                WsEnvelope<Object> error = new WsEnvelope<>(
                    "error",
                    envelope.getRequestId(),
                    Map.of(
                        "code", "UNKNOWN_EVENT",
                        "message", "Unknown event type" + envelope.getType())

                );

                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(error)));

            }
    }
    
}
}