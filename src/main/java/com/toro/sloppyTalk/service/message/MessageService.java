package com.toro.sloppyTalk.service.message;

import com.toro.sloppyTalk.domain.Message;

import java.util.List;

public interface MessageService {


    Long save(Message message);

    List<Message> findMessageByChatRoomId(Long chatRoomId);
}
