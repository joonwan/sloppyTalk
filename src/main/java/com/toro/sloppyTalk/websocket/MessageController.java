package com.toro.sloppyTalk.websocket;

import com.toro.sloppyTalk.domain.Message;
import com.toro.sloppyTalk.service.chatroom.ChatRoomService;
import com.toro.sloppyTalk.service.message.MessageService;
import com.toro.sloppyTalk.websocket.dto.MessageDto;
import com.toro.sloppyTalk.websocket.dto.ResponseMessageDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import javax.print.attribute.standard.OrientationRequested;
import java.security.Principal;
import java.util.Enumeration;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final ActiveUserManager userManager;

    @MessageMapping("/private")
    public void sendPrivateMessage( MessageDto dto, @Header("simpSessionId") String sessionId){

        String chatRoomId = dto.getChatRoomId().toString();
        Long lChatRoomId = Long.valueOf(chatRoomId);
        Long fromId = dto.getFromId();
        Long toId = dto.getToId();

        boolean active = userManager.isActive(toId,lChatRoomId);
        log.info("Is memberId = {} active ? => {}", toId, active );
        String content = dto.getContent();
        messageService.save(dto);


        if(!active){
            messagingTemplate.convertAndSend("/private_alert/" + toId,new ResponseMessageDto(fromId, toId, content));
        }
        messagingTemplate.convertAndSend("/chat_room/" + chatRoomId, new ResponseMessageDto(fromId, toId, content));

    }

}
