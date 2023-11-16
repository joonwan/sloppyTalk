package com.toro.sloppyTalk.service.message;

import com.toro.sloppyTalk.Repository.message.MessageRepository;
import com.toro.sloppyTalk.domain.ChatRoom;
import com.toro.sloppyTalk.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;

    @Override
    @Transactional(readOnly = false)
    public Long save(Message message){
        return messageRepository.save(message);
    };

    @Override
    public List<Message> findMessageByChatRoomId(Long chatRoomId){
        return messageRepository.findMessages(chatRoomId);
    }
}
