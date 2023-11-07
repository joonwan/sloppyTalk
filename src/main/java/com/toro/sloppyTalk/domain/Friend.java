package com.toro.sloppyTalk.domain;

import jakarta.persistence.*;

@Entity
public class Friend {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;



}
