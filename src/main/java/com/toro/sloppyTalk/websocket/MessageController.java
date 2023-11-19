package com.toro.sloppyTalk.websocket;

import com.toro.sloppyTalk.domain.Message;
import com.toro.sloppyTalk.service.chatroom.ChatRoomService;
import com.toro.sloppyTalk.service.message.MessageService;
import com.toro.sloppyTalk.websocket.dto.MessageDto;
import com.toro.sloppyTalk.websocket.dto.ResponseMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    // if client subscribe "/topic/global", client can receive message
//    @MessageMapping("/global")
//    @SendTo("/topic/global")
//    public ResponseMessageDto getGlobalMessage(MessageDto dto){
//        return new ResponseMessageDto(dto.getFromId(), dto.getToId(),
//                "global message : " + HtmlUtils.htmlEscape(dto.getContent()));
//    }

    @MessageMapping("/private")
    public void sendPrivateMessage(MessageDto dto){
        String chatRoomId = dto.getChatRoomId().toString();
        Long fromId = dto.getFromId();
        Long toId = dto.getToId();

        String content = dto.getContent();
        messageService.save(dto);

        messagingTemplate.convertAndSend("/chat_room/" + chatRoomId, new ResponseMessageDto(fromId, toId, content));
    }

}
