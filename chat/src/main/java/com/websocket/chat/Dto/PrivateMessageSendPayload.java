package com.websocket.chat.Dto;

public class PrivateMessageSendPayload {
    private Integer toUserId;
    private String body;

    
    public PrivateMessageSendPayload() {
    }
    public Integer getToUserId() {
        return toUserId;
    }
    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    
}
