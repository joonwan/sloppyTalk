package com.toro.sloppyTalk.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Message {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member member;

    private String content;

    private LocalDateTime createDateTime;

    public Message() {
    }

    public Message(ChatRoom chatRoom, Member member, String content, LocalDateTime createDateTime) {
        this.chatRoom = chatRoom;
        this.member = member;
        this.content = content;
        this.createDateTime = createDateTime;

        chatRoom.getMessages().add(this);
    }
}
