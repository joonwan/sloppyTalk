package com.toro.sloppyTalk.login;

import com.toro.sloppyTalk.domain.Member;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SessionManagerImpl {

    private final Map<String, Member> sessionStore = new HashMap<>();
    private final Map<Long, String> idWithSessionIdStore = new HashMap<>();

    public String createSession(Member member){
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId,member);
        idWithSessionIdStore.put(member.getId(),sessionId);
        return sessionId;
    }


    public Long getMemberId(String sessionId) {
        return sessionStore.get(sessionId).getId();
    }
}
