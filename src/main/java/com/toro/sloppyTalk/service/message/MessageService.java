package com.toro.sloppyTalk.service.message;

import com.toro.sloppyTalk.domain.Message;
import com.toro.sloppyTalk.websocket.dto.MessageDto;

import java.util.List;

public interface MessageService {


    Long save(MessageDto messageDto);

    List<Message> findMessageByChatRoomId(Long chatRoomId);
}
