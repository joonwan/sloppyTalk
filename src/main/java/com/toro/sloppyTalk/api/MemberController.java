package com.toro.sloppyTalk.api;

import com.toro.sloppyTalk.domain.ChatRoom;
import com.toro.sloppyTalk.domain.Friend;
import com.toro.sloppyTalk.domain.Member;
import com.toro.sloppyTalk.login.SessionManagerImpl;
import com.toro.sloppyTalk.service.chatroom.ChatRoomService;
import com.toro.sloppyTalk.service.member.MemberService;
import com.toro.sloppyTalk.service.message.MessageService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final SessionManagerImpl sessionManager;
    private final ChatRoomService chatRoomService;
    private final MessageService messageService;


    @PostMapping("/new")
    public void join(@RequestBody RegisterDto param){
        Member member = getMember(param);
        memberService.save(member);
    }

    @GetMapping("/{sessionId}")
    public List<MembersResponseDto> getMembers(@PathVariable String sessionId){
        Long userId = sessionManager.getMemberId(sessionId);
        List<Member> members = memberService.findMembers(userId);
        return members.stream().map(MembersResponseDto::new).collect(Collectors.toList());
    }

    @PostMapping("/{sessionId}/follow")
    public String follow(@PathVariable String sessionId, @RequestBody FollowRequestDto dto){

        Long memberId = sessionManager.getMemberId(sessionId);
        Long targetId = dto.getTargetId();
        memberService.follow(memberId,targetId);
        return "ok";
    }

    @GetMapping("/{sessionId}/friends")
    public List<FriendsResponseDto> getFriends(@PathVariable String sessionId){
        Long memberId = sessionManager.getMemberId(sessionId);
        List<Friend> friends = memberService.findFriends(memberId);
        return friends.stream().map(FriendsResponseDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{memberId}/chatroom")
    public List<MemberChatRoomsDto> getChatRooms(@PathVariable Long memberId){
        List<ChatRoom> chatRooms = chatRoomService.findChatRoomByMemberId(memberId);
        List<MemberChatRoomsDto> result = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms) {
            Long chatRoomId = chatRoom.getId();

            String lastContent = messageService.getLastMessage(chatRoomId);
            Member friend = chatRoomService.getFriend(chatRoomId, memberId);
            MemberChatRoomsDto dto = new MemberChatRoomsDto(chatRoomId, lastContent, friend.getId(), friend.getName());
            result.add(dto);
        }

        return result;
    }

    private static Member getMember(RegisterDto param) {
        String name = param.getName();
        String loginId = param.getLoginId();
        String password = param.getPassword();

        return new Member(name,loginId,password);
    }

    @Data
    static class FriendsResponseDto{

        private Long friendId;
        private String friendName;

        public FriendsResponseDto(Friend friend) {

            this.friendId = friend.getOther().getId();
            this.friendName = friend.getOther().getName();
        }
    }

    @Data
    static class RegisterDto{

        private String name;
        private String loginId;
        private String password;
    }

    @Data
    static class MembersResponseDto{
        private String memberName;
        private Long memberId;

        public MembersResponseDto(Member m) {
            this.memberName = m.getName();
            this.memberId = m.getId();
        }
    }

    @Data
    static class FollowRequestDto{

        private Long targetId;

        public FollowRequestDto() {
        }

        public FollowRequestDto(Long targetId) {
            this.targetId = targetId;
        }
    }

    @Data
    static class MemberChatRoomsDto{
        private Long chatRoomId;
        private String content;
        private Long friendId;
        private String friendName;

        public MemberChatRoomsDto(Long chatRoomId, String content, Long friendId, String friendName) {
            this.chatRoomId = chatRoomId;
            this.content = content;
            this.friendId = friendId;
            this.friendName = friendName;
        }
    }
}
