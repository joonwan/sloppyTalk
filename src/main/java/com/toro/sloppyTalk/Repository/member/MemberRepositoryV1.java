package com.toro.sloppyTalk.Repository.member;

import com.toro.sloppyTalk.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public Member findByLoginId(String loginId){
        return em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
                .setParameter("loginId",loginId)
                .getSingleResult();
    }

}
