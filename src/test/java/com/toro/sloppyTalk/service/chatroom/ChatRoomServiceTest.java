package com.toro.sloppyTalk.service.chatroom;

import com.toro.sloppyTalk.domain.Member;
import com.toro.sloppyTalk.domain.MemberChatRoom;
import com.toro.sloppyTalk.service.member.MemberService;
import com.toro.sloppyTalk.service.message.MessageService;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ChatRoomServiceTest {

    @Autowired
    ChatRoomService chatRoomService;

    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;

    @Autowired
    MessageService messageService;

    @Test
    @Commit
    public void createChatRoom() throws Exception {
        //given
        Member member1 = new Member("test1","test1","test1");
        Member member2 = new Member("test2","test2","test2");

        memberService.save(member1);
        memberService.save(member2);

        em.flush();
        em.clear();
        //when

        List<Member> parcitipantList = Arrays.asList(member1, member2);

        chatRoomService.createChatRoom(parcitipantList);

        em.flush();
        em.clear();
        //then

        Member findMember1 = memberService.findMember(member1.getId());
        Member findMember2 = memberService.findMember(member2.getId());

        List<MemberChatRoom> memberChatRooms1 = findMember1.getMemberChatRooms();
        List<MemberChatRoom> memberChatRooms2 = findMember2.getMemberChatRooms();

        Assertions.assertThat(memberChatRooms1.size()).isEqualTo(1);
        Assertions.assertThat(memberChatRooms2.size()).isEqualTo(1);
    }

}