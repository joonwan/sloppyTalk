package com.toro.sloppyTalk.Repository.message;

import com.toro.sloppyTalk.domain.Message;

import java.util.List;

public interface MessageRepository {

    Long save(Message message);

    List<Message> findMessages(Long chatRoomId);
}
