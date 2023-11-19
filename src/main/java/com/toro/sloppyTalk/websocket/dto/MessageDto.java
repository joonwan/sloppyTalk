package com.toro.sloppyTalk.websocket.dto;

import lombok.Data;

@Data
public class MessageDto {
    private Long fromId;
    private Long toId;
    private String content;
    private Long chatRoomId;

}
