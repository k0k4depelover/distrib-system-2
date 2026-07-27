package com.websocket.chat.Config;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.chat.Dto.PrivateMessageSendPayload;
import com.websocket.chat.Dto.WsEnvelope;

/**
 * PrivateMessageHandler
 */

@Component
public class PrivateMessageHandler {
    private final SessionRegistry sessionRegistry;
    private final ObjectMapper objectMapper;


    public PrivateMessageHandler(SessionRegistry sessionRegistry, ObjectMapper objectMapper) {
        this.sessionRegistry = sessionRegistry;
        this.objectMapper = objectMapper;
    }  

    public void handle(
        Integer senderUserId,
        Integer requestId, 
        PrivateMessageSendPayload payload
    ) throws IOException{
        String messageId = UUID.randomUUID().toString();

        sendAckToSender(senderUserId, requestId, messageId);

        sendMessageToReceiver(senderUserId, payload, messageId, requestId);

    }


    private void sendAckToSender( Integer sendUserId, Integer requestId, String messageId)
    throws IOException{

        WsEnvelope<Object> ack = new WsEnvelope<>(
            "private.message.ack",
            requestId,
            Map.of(
                "messageId", messageId,
                "status", "accepted"
            )
        );
        
        sessionRegistry.sendToUser(sendUserId, objectMapper.writeValueAsString(ack));
    }

    private void sendMessageToReceiver(Integer senderUserId, PrivateMessageSendPayload payload,
        String messageId, Integer requestId
    ) throws IOException{
        WsEnvelope<Object> received = new WsEnvelope<>(
            "private.message.received",
            null,
            Map.of(
                "messageId", messageId,
                "fromUserId", senderUserId,
                "body", payload.getBody(),
                "serverTime", Instant.now().toString()
            )
        );
        try{
            sessionRegistry.sendToUser(payload.getToUserId(), objectMapper.writeValueAsString(received));
        }
        catch(IllegalStateException e){
            WsEnvelope<Object> error = new WsEnvelope<>(
                "error",
                requestId,
                Map.of(
                    "code", "USER_OFFLINE",
                    "message", "Receiver is not connected"
                )
            );
            sessionRegistry.sendToUser(senderUserId, objectMapper.writeValueAsString(error));
        }
    }
    
}
