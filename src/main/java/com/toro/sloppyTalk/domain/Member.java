package com.toro.sloppyTalk.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private String loginId;
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Friend> friends = new ArrayList<>();

    public Member() {
    }

    public Member(String name, String loginId, String password) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
    }
}
