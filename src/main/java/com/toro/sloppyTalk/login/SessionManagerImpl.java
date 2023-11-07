package com.toro.sloppyTalk.login;

import com.toro.sloppyTalk.domain.Member;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SessionManagerImpl {

    private final Map<String, Member> sessionStore = new HashMap<>();

    public String createSession(Member member){
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId,member);
        return sessionId;
    }


}
