package com.toro.sloppyTalk.service.chatroom;

import com.toro.sloppyTalk.Repository.chatroom.ExistDto;
import com.toro.sloppyTalk.domain.ChatRoom;
import com.toro.sloppyTalk.domain.Member;
import com.toro.sloppyTalk.domain.MemberChatRoom;

import java.util.List;

public interface ChatRoomService {

    Long createChatRoom(List<Long> participantIdList);

    ChatRoom findChatRoom(Long chatRoomId);

    ExistDto alreadyExist(Long memberId, Long friendId);

    List<ChatRoom> findChatRoomByMemberId(Long memberId);

    Member getFriend(long chatRoomId, Long memberId);
}
