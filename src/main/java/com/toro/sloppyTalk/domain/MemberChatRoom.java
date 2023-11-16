package com.toro.sloppyTalk.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MemberChatRoom {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    public MemberChatRoom() {
    }

    public MemberChatRoom(Member member, ChatRoom chatRoom) {
        this.member = member;
        this.chatRoom = chatRoom;

        member.getMemberChatRooms().add(this);
    }
}
