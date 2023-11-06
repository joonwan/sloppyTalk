package com.toro.sloppyTalk.Repository.member;

import com.toro.sloppyTalk.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryV1 implements MemberRepository{

    @PersistenceContext
    private EntityManager em;


    @Override
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    @Override
    public Member findById(Long memberId) {
        return em.find(Member.class, memberId);
    }

}
