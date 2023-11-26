package com.toro.sloppyTalk.Repository.message;

import com.toro.sloppyTalk.domain.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepositoryImpl implements MessageRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long save(Message message){
        em.persist(message);
        return message.getId();
    }
    @Override
    public List<Message> findMessages(Long chatRoomId){
        return em.createQuery("select m from Message m where m.chatRoom.id = :chatRoomId", Message.class)
                .setParameter("chatRoomId", chatRoomId)
                .getResultList();
    }

    @Override
    public String findLastMessage(Long chatRoomId){
        return em.createQuery("select  m.content from Message m where m.chatRoom.id = :chatRoomId and m.createDateTime = (select max(m2.createDateTime) from Message m2 where m.chatRoom = m2.chatRoom)", String.class)
                .setParameter("chatRoomId", chatRoomId)
                .getSingleResult();
    }
}
