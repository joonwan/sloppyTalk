package com.toro.sloppyTalk.service.message;

import com.toro.sloppyTalk.Repository.chatroom.ChatRoomRepository;
import com.toro.sloppyTalk.Repository.member.MemberRepository;
import com.toro.sloppyTalk.Repository.message.MessageRepository;
import com.toro.sloppyTalk.domain.ChatRoom;
import com.toro.sloppyTalk.domain.Member;
import com.toro.sloppyTalk.domain.Message;
import com.toro.sloppyTalk.websocket.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = false)
    public Long save(MessageDto dto){

        Message message = createMessage(dto);
        messageRepository.save(message);

        return message.getId();

    }

    private Message createMessage(MessageDto dto) {
        Long senderId = dto.getFromId();
        Long receiverId = dto.getToId();
        Long chatRoomId = dto.getChatRoomId();
        String content = dto.getContent();

        Member sender = memberRepository.findById(senderId);
        Member receiver = memberRepository.findById(receiverId);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);

        return new Message(chatRoom, sender, content, LocalDateTime.now());
    }

    ;

    @Override
    public List<Message> findMessageByChatRoomId(Long chatRoomId){
        return messageRepository.findMessages(chatRoomId);
    }

    @Override
    public String getLastMessage(Long chatRoomId){
        return messageRepository.findLastMessage(chatRoomId);
    }


}
