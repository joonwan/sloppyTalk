package com.toro.sloppyTalk.service.chatroom;

import com.toro.sloppyTalk.domain.ChatRoom;
import com.toro.sloppyTalk.domain.Member;
import com.toro.sloppyTalk.domain.MemberChatRoom;

import java.util.List;

public interface ChatRoomService {

    Long createChatRoom(List<Member> participantList);

    ChatRoom findChatRoom(Long chatRoomId);


}
