package com.toro.sloppyTalk.websocket;

import com.toro.sloppyTalk.websocket.dto.Message;
import com.toro.sloppyTalk.websocket.dto.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {

    // if client subscribe "/topic/global", client can receive message
    @MessageMapping("/global")
    @SendTo("/topic/global")
    public ResponseMessage getGlobalMessage(Message message){
        return new ResponseMessage(message.getFromId(), message.getToId(),
                "global message : " + HtmlUtils.htmlEscape(message.getContent()));
    }

}
