package com.toro.sloppyTalk.websocket.dto;

import lombok.Data;

@Data
public class Message {
    private Long fromId;
    private Long toId;
    private String content;

}
