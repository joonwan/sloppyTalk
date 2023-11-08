package com.toro.sloppyTalk.Repository.friend;

import com.toro.sloppyTalk.domain.Friend;
import com.toro.sloppyTalk.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class FriendRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Friend friend){
        log.info("{}",friend.getMe().getName());
        log.info("{}",friend.getOther().getName());
        em.persist(friend);
        return friend.getId();
    }

    public List<Friend> findFriends(Member member){
        return em.createQuery("select f from Friend f where f.me = :member", Friend.class)
                .setParameter("member",member)
                .getResultList();
    }
}
