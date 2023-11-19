package com.toro.sloppyTalk.service.chatroom;

import com.toro.sloppyTalk.Repository.chatroom.ChatRoomRepository;
import com.toro.sloppyTalk.Repository.chatroom.ExistDto;
import com.toro.sloppyTalk.Repository.chatroom.MemberChatRoomRepository;
import com.toro.sloppyTalk.Repository.member.MemberRepository;
import com.toro.sloppyTalk.Repository.message.MessageRepository;
import com.toro.sloppyTalk.domain.ChatRoom;
import com.toro.sloppyTalk.domain.Member;
import com.toro.sloppyTalk.domain.MemberChatRoom;
import com.toro.sloppyTalk.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberChatRoomRepository memberChatRoomRepository;


    @Override
    @Transactional(readOnly = false)
    public Long createChatRoom(List<Long> participantIdList){

        // 채팅방 생성
        ChatRoom chatRoom = new ChatRoom(LocalDateTime.now());
        chatRoomRepository.save(chatRoom);

        //참가자 호출 후 memberChatroom row 생성후 저장

        for(Long participantId : participantIdList){
            Member participant = memberRepository.findById(participantId);
            MemberChatRoom memberChatRoom = new MemberChatRoom(participant, chatRoom);
            memberChatRoomRepository.save(memberChatRoom);
        }

        return chatRoom.getId();
    }

    @Override
    public ChatRoom findChatRoom(Long chatRoomId){
        return chatRoomRepository.findById(chatRoomId);
    }


    @Override
    public ExistDto alreadyExist(Long memberId, Long friendId){
        return memberChatRoomRepository.alreadyExist(memberId, friendId);
    }

}


