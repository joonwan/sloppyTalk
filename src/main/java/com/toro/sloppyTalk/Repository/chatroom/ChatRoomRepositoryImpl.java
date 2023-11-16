package com.toro.sloppyTalk.Repository.chatroom;

import com.toro.sloppyTalk.domain.ChatRoom;
import com.toro.sloppyTalk.domain.Member;
import com.toro.sloppyTalk.domain.MemberChatRoom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatRoomRepositoryImpl implements ChatRoomRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long save(ChatRoom chatRoom){
        em.persist(chatRoom);
        return chatRoom.getId();
    }

    @Override
    public ChatRoom findById(Long chatRoomId){
        return em.find(ChatRoom.class, chatRoomId);
    }

}
