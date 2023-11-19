package com.toro.sloppyTalk.api;

import com.toro.sloppyTalk.Repository.chatroom.ExistDto;
import com.toro.sloppyTalk.domain.Member;
import com.toro.sloppyTalk.service.chatroom.ChatRoomService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

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

}
