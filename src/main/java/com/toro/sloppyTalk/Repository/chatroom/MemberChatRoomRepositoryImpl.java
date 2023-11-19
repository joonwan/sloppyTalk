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

    @Override
    public ExistDto alreadyExist(Long memberId, Long friendId){

        List<Long> resultList = em.createQuery("select mcr.chatRoom.id from MemberChatRoom mcr " +
                        "where mcr.member.id in (:memberId, : friendId) group by mcr.chatRoom.id having count (distinct mcr.member) = 2", Long.class)
                .setParameter("memberId", memberId)
                .setParameter("friendId", friendId)
                .getResultList();

        if(resultList.size() == 1){
            return new ExistDto(resultList.get(0),true);
        }

        return new ExistDto(null,false);

    }
}
