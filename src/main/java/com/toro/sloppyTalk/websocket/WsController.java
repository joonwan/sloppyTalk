package com.toro.sloppyTalk.websocket;

import jdk.jfr.Frequency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/ws")
@RequiredArgsConstructor
public class WsController {

    private final SimpUserRegistry userRegistry;

    @GetMapping
    public void getActiveUsers(){
        log.info("=== detect active users ===");
        Set<SimpUser> users = userRegistry.getUsers();
        for (SimpUser user : users) {
            String name = user.getName();
            log.info("name = {}",name);

            Set<SimpSession> sessions = user.getSessions();
            for (SimpSession session : sessions) {
                String sessionId = session.getId();
                log.info("user name = {}, sessionId = {}", name, sessionId);
            }
        }

    }
}
