package com.toro.sloppyTalk.Repository.chatroom;

import com.toro.sloppyTalk.domain.MemberChatRoom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberChatRoomRepositoryImpl implements MemberChatRoomRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long save(MemberChatRoom memberChatRoom){
        em.persist(memberChatRoom);
        return memberChatRoom.getId();
    }

    @Override
    public MemberChatRoom findById(Long memberChatRoomId){
        return em.find(MemberChatRoom.class, memberChatRoomId);
    }

    @Override
    public List<MemberChatRoom> findMemberChatRooms(Long memberId){
        return em.createQuery("select mcr from MemberChatRoom mcr where mcr.member.id = :memberrId", MemberChatRoom.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
