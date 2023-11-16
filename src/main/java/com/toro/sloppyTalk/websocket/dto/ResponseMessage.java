package com.toro.sloppyTalk.websocket.dto;

import lombok.Data;

@Data
public class ResponseMessage {

    private Long fromId;
    private Long toId;
    private String content;

    public ResponseMessage(Long fromId, Long toId, String content) {
        this.fromId = fromId;
        this.toId = toId;
        this.content = content;
    }
}
