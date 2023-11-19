package com.toro.sloppyTalk.websocket.dto;

import lombok.Data;

@Data
public class ResponseMessageDto {

    private Long fromId;
    private Long toId;
    private String content;

    public ResponseMessageDto(Long fromId, Long toId, String content) {
        this.fromId = fromId;
        this.toId = toId;
        this.content = content;
    }
}
