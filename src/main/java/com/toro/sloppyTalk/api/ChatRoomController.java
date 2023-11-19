package com.toro.sloppyTalk.api;

import com.toro.sloppyTalk.Repository.chatroom.ExistDto;
import com.toro.sloppyTalk.domain.Message;
import com.toro.sloppyTalk.service.chatroom.ChatRoomService;
import com.toro.sloppyTalk.service.message.MessageService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final MessageService messageService;

    @PostMapping("/new")
    public ChatRoomCreateResponseDto createChatRoom(@RequestBody ChatRoomCreateRequestDto dto){

        Long memberId = dto.getMemberId();
        Long friendId = dto.getFriendId();
        log.info("find chat room member {}, {}", memberId, friendId);
        ExistDto alreadyExist = chatRoomService.alreadyExist(memberId, friendId);

        boolean isAlreadyExist = alreadyExist.isAlreadyExist();

        if(isAlreadyExist){
            Long chatRoomId = alreadyExist.getChatRoomId();
            log.info("exist = {}", chatRoomId);
            return new ChatRoomCreateResponseDto(chatRoomId);
        }

        List<Long> participantIdList = getParticipantIdList(dto);
        Long chatRoomId = chatRoomService.createChatRoom(participantIdList);
        log.info("create chat room id = {}", chatRoomId);
        return new ChatRoomCreateResponseDto(chatRoomId);

    }

    @GetMapping("/{chatRoomId}/messages")
    public MessageResponseDto getMessages(@PathVariable Long chatRoomId){
        int id = 1;
        List<MessageData> messageDataList = new ArrayList<>();
        List<Message> messages = messageService.findMessageByChatRoomId(chatRoomId);
        for (Message message : messages) {
            Long fromId = message.getMember().getId();
            String content = message.getContent();
            MessageData data = new MessageData(id,fromId,content);
            messageDataList.add(data);
            id ++;
        }

        return new MessageResponseDto(id, messageDataList);
    }


    private static List<Long> getParticipantIdList(ChatRoomCreateRequestDto dto) {
        Long memberId = dto.getMemberId();
        Long friendId = dto.getFriendId();
        return Arrays.asList(memberId, friendId);
    }

    @Data
    static class ChatRoomCreateRequestDto{

        Long memberId;
        Long friendId;

        public ChatRoomCreateRequestDto() {
        }

        public ChatRoomCreateRequestDto(Long memberId, Long friendId) {
            this.memberId = memberId;
            this.friendId = friendId;
        }
    }

    @Data
    static class ChatRoomCreateResponseDto{
        Long chatRoomId;
        boolean already;
        public ChatRoomCreateResponseDto(Long chatRoomId){
            this.chatRoomId = chatRoomId;
        }
    }

    @Data
    static class MessageData{
        private int id;
        private Long fromId;
        private String content;

        public MessageData(int id, Long fromId, String content) {
            this.id = id;
            this.fromId = fromId;
            this.content = content;
        }
    }

    @Data
    static class MessageResponseDto{

        private int startId;
        private List<MessageData> messageDataList;
        public MessageResponseDto(int startId, List<MessageData> messageDataList) {
            this.startId = startId;
            this.messageDataList = messageDataList;
        }
    }

}
