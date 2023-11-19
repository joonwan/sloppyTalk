package com.toro.sloppyTalk.Repository.chatroom;

import lombok.Data;

@Data
public class ExistDto {
    Long chatRoomId;
    boolean alreadyExist;

    public ExistDto(Long chatRoomId, boolean alreadyExist) {
        this.chatRoomId = chatRoomId;
        this.alreadyExist = alreadyExist;
    }
}
