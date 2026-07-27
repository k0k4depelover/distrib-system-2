package com.websocket.chat.Dto;

public class WsEnvelope<T> {
    private String type;
    private Integer requestId;
    private T payload;

    public WsEnvelope(String type, Integer requestId, T payload) {
        this.type = type;
        this.requestId = requestId;
        this.payload = payload;
    }

    public WsEnvelope(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    
}
