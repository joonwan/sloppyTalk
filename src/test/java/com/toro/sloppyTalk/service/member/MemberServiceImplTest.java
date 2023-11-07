package com.toro.sloppyTalk.service.member;

import com.toro.sloppyTalk.domain.Member;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;

    @Test
    public void saveAndFind() throws Exception {
        //given
        Member member1 = new Member("member1", "id", "passw");
        Member member2 = new Member("member2", "id", "passw");

        Long member1Id = memberService.save(member1);
        Long member2Id = memberService.save(member2);

        em.flush();
        em.clear();
        //when

        Member findMember = memberService.findMember(member1Id);

        //then

        Assertions.assertThat(findMember.getName()).isEqualTo(member1.getName());
        Assertions.assertThat(findMember.getName()).isNotEqualTo(member2.getName());

    }

    @Test
    public void login() throws Exception {
        //given
        Member member1 = new Member("member1", "id", "passw");
        memberService.save(member1);

        em.flush();
        em.clear();

        //when
        Member findMember = memberService.findMember(member1.getId());
        Member loginMember = memberService.findLoginMember(member1.getLoginId());

        //then
        Assertions.assertThat(loginMember).isEqualTo(findMember);
    }
}