package com.toro.sloppyTalk.Repository;

import com.toro.sloppyTalk.Repository.member.MemberRepository;
import com.toro.sloppyTalk.domain.Member;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void saveAndFind() throws Exception {
        //given
        Member member1 = new Member("member1","id","passw");
        Member member2 = new Member("member2","id","passw");

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        em.flush();
        em.clear();
        Member findMember = memberRepository.findById(member1.getId());

        //then
        Assertions.assertThat(findMember.getName()).isEqualTo(member1.getName());
        Assertions.assertThat(findMember.getName()).isNotEqualTo(member2.getName());
    }

    @Test
    public void findAllTest() throws Exception {
        //given
        Member member1 = new Member("member1","asd","asd");
        Member member2 = new Member("member2","asd","asd");
        Member member3 = new Member("member3","asd","asd");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        em.flush();
        em.clear();
        //when

        List<Member> members = memberRepository.findAll(member1.getId());

        members.stream().forEach(m -> System.out.println(m.getName()));

        //then

        Assertions.assertThat(members.size()).isEqualTo(2);
    }
}