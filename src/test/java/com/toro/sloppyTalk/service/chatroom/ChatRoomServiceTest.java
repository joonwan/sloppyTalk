package com.toro.sloppyTalk.service.chatroom;

import com.toro.sloppyTalk.Repository.chatroom.ExistDto;
import com.toro.sloppyTalk.domain.Member;
import com.toro.sloppyTalk.domain.MemberChatRoom;
import com.toro.sloppyTalk.service.member.MemberService;
import com.toro.sloppyTalk.service.message.MessageService;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;



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

        Long member1Id = memberService.save(member1);
        Long member2Id = memberService.save(member2);

        em.flush();
        em.clear();
        //when

        List<Long> parcitipantList = Arrays.asList(member1Id, member2Id);

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

        List<MemberChatRoom> memberChatRooms = findMember1.getMemberChatRooms();
        for (MemberChatRoom memberChatRoom3 : memberChatRooms) {
            System.out.println(memberChatRoom3.getChatRoom().getId());
        }
        List<MemberChatRoom> memberChatRooms4 = findMember2.getMemberChatRooms();
        for (MemberChatRoom memberChatRoom : memberChatRooms2) {
            System.out.println(memberChatRoom.getChatRoom().getId());
        }
    }

    @Test
    @DisplayName("채팅방 존재 여부 쿼리 테스트")
    @Commit
    public void test() throws Exception {
        //given
        Member member1 = new Member("test1","test1","test1");
        Member member2 = new Member("test2","test2","test2");

        Long member1Id = memberService.save(member1);
        Long member2Id = memberService.save(member2);

        //when
        List<Long> resultList = em.createQuery("select mcr.chatRoom.id from MemberChatRoom mcr where mcr.member.id in (:member1Id, :member2Id) group by mcr.chatRoom.id", Long.class)

                .setParameter("member1Id", member1.getId())
                .setParameter("member2Id",member2.getId())
                .getResultList();

        Assertions.assertThat(resultList.size()).isEqualTo(0);

        Long chatRoomId = chatRoomService.createChatRoom(Arrays.asList(member1Id, member2Id));
        System.out.println("chat room id = " + String.valueOf(chatRoomId));

        em.flush();
        em.clear();
        System.out.println("member1 id = " + String.valueOf(member1Id));
        System.out.println("member2 id = " + String.valueOf(member2Id));
        List<Long> resultList1 = em.createQuery("select mcr.chatRoom.id from MemberChatRoom mcr where mcr.member.id in (:member1Id, :member2Id) group by mcr.chatRoom.id", Long.class)

                .setParameter("member1Id", member1.getId())
                .setParameter("member2Id",member2.getId())
                .getResultList();

        Assertions.assertThat(resultList1.size()).isEqualTo(1);

        Assertions.assertThat(resultList1.get(0)).isEqualTo(chatRoomId);
        //then
    }

    @Test
    @Commit
    @DisplayName("chat room 중복 생성 방지 test")
    public void test2() throws Exception {
        //given
        Member member1 = new Member("test1","test1","test1");
        Member member2 = new Member("test2","test2","test2");
        Member member3 = new Member("test3","test3","test3");

        Long member1Id = memberService.save(member1);
        Long member2Id = memberService.save(member2);
        Long member3Id = memberService.save(member3);
        //when
        ExistDto existDto = chatRoomService.alreadyExist(member1Id, member2Id);
        Assertions.assertThat(existDto.isAlreadyExist()).isFalse();

        chatRoomService.createChatRoom(Arrays.asList(member1Id,member2Id));

        em.flush();
        em.clear();

        ExistDto existDto1 = chatRoomService.alreadyExist(member1Id, member2Id);
        Assertions.assertThat(existDto1.isAlreadyExist()).isTrue();



        ExistDto existDto2 = chatRoomService.alreadyExist(member1Id, member3Id);
        Assertions.assertThat(existDto2.isAlreadyExist()).isFalse();
        //then


    }

}