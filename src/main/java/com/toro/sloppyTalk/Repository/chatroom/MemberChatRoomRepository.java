package com.toro.sloppyTalk.Repository.chatroom;

import com.toro.sloppyTalk.domain.ChatRoom;
import com.toro.sloppyTalk.domain.Member;
import com.toro.sloppyTalk.domain.MemberChatRoom;

import java.util.List;

public interface MemberChatRoomRepository {

    Long save(MemberChatRoom memberChatRoom);

    MemberChatRoom findById(Long memberChatRoomId);

    List<ChatRoom> findMemberChatRooms(Long memberId);

    ExistDto alreadyExist(Long memberId, Long friendId);

    Member getFriend(Long chatRoomId, Long memberId);
}
