package com.toro.sloppyTalk.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Friend {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member me;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private Member other;

    public Friend() {
    }

    public Friend(Member me, Member other) {
        this.me = me;
        this.other = other;
    }
}
