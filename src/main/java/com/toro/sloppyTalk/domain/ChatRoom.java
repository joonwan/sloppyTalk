package com.toro.sloppyTalk.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class ChatRoom {

    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "chatRoom")
    private List<Message> messages = new ArrayList<>();

    private LocalDateTime createDateTime;

    public ChatRoom() {
    }

    public ChatRoom(LocalDateTime createDateTime) {

        this.createDateTime = createDateTime;
    }
}
