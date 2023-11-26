package com.toro.sloppyTalk.websocket;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class ActiveUserManager {

    private static final Map<String, SessionObject> activeUsers = new HashMap<>();

    public void addUser(String sessionId, Long memberId, Long chatRoomId){
        SessionObject sessionObject = new SessionObject(memberId, chatRoomId);
        activeUsers.put(sessionId,sessionObject);
    }

    public void removeUser(String sessionId){
        activeUsers.remove(sessionId);
    }

    public int getActiveUserCount(){
        return activeUsers.size();
    }

    public boolean isActive(Long memberId, Long chatRoomId){
        Set<String> keySet = activeUsers.keySet();
        log.info("==== detect user  ====");
        for (String key : keySet) {

            SessionObject sessionObject = activeUsers.get(key);
            Long userMemberId = sessionObject.getMemberId();
            Long userChatRoomId = sessionObject.getChatRoomId();

            if(memberId.equals(userMemberId) && chatRoomId.equals(userChatRoomId)){
                return true;
            }
        }

        return false;
    }

    @Data
    static class SessionObject{

        private Long memberId;
        private Long chatRoomId;

        public SessionObject(Long memberId, Long chatRoomId) {
            this.memberId = memberId;
            this.chatRoomId = chatRoomId;
        }
    }
}
