package com.toro.sloppyTalk.Repository.chatroom;

import com.toro.sloppyTalk.domain.MemberChatRoom;

import java.util.List;

public interface MemberChatRoomRepository {

    Long save(MemberChatRoom memberChatRoom);

    MemberChatRoom findById(Long memberChatRoomId);

    List<MemberChatRoom> findMemberChatRooms(Long memberId);
}
