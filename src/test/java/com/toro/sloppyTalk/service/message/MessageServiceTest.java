package com.toro.sloppyTalk.service.message;

import com.toro.sloppyTalk.domain.ChatRoom;
import com.toro.sloppyTalk.domain.Member;
import com.toro.sloppyTalk.domain.Message;
import com.toro.sloppyTalk.service.chatroom.ChatRoomService;
import com.toro.sloppyTalk.service.member.MemberService;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
class MessageServiceTest {


    @Autowired
    MemberService memberService;

    @Autowired
    MessageService messageService;

    @Autowired
    ChatRoomService chatRoomService;

    @Autowired
    EntityManager em;


    @Test
    @Commit
    public void messageServiceTest() throws Exception {
        //given

        Member member1 = new Member("test1","test1","test1");
        Member member2 = new Member("test2","test2","test2");

        Long member1Id = memberService.save(member1);
        Long member2Id = memberService.save(member2);

        memberService.save(member1);
        memberService.save(member2);

        em.flush();
        em.clear();

        List<Long> participantIdList = Arrays.asList(member1Id, member2Id);

        Long chatRoomId = chatRoomService.createChatRoom(participantIdList);

        em.flush();
        em.clear();

        ChatRoom chatRoom = chatRoomService.findChatRoom(chatRoomId);

        //when
        Message message1 = new Message(chatRoom,member1,"new Test Message1",LocalDateTime.now());
        Message message2 = new Message(chatRoom,member1,"new Test Message2",LocalDateTime.now());

        em.persist(message1);
        em.persist(message2);

        //then

        List<Message> messages = messageService.findMessageByChatRoomId(chatRoomId);
        Assertions.assertThat(messages.size()).isEqualTo(2);

        //last message test

        em.flush();
        em.clear();

        List<Object[]> resultList =em.createQuery("select m.content, m.member.name from Message m where m.id = (select max(m2.id) from Message m2)")
                .getResultList();

        for(Object[] row : resultList){
            System.out.println("row = " + row[0]);
            System.out.println("row = " + row[1]);
        }

    }

    @Test
    public void test3() throws Exception {
        //given
        Member member1 = new Member("test1","test1","test1");
        Member member2 = new Member("test2","test2","test2");

        Long member1Id = memberService.save(member1);
        Long member2Id = memberService.save(member2);

        memberService.save(member1);
        memberService.save(member2);

        em.flush();
        em.clear();

        List<Long> participantIdList = Arrays.asList(member1Id, member2Id);

        Long chatRoomId = chatRoomService.createChatRoom(participantIdList);

        em.flush();
        em.clear();

        ChatRoom chatRoom = chatRoomService.findChatRoom(chatRoomId);

        //when
        Message message1 = new Message(chatRoom,member1,"new Test Message1",LocalDateTime.now());
        Message message2 = new Message(chatRoom,member1,"new Test Message2",LocalDateTime.now());

        em.persist(message1);
        em.persist(message2);
        //when

        String name = em.createQuery("select mcr.member.name from MemberChatRoom mcr where chatRoom.id = :chatRoomId and mcr.member.id <> :memberId", String.class)
                .setParameter("chatRoomId", chatRoomId)
                .setParameter("memberId", member1Id)
                .getSingleResult();

        System.out.println("name = " + name);

        //then
    }


}