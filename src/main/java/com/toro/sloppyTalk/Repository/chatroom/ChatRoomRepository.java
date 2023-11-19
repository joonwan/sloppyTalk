package com.toro.sloppyTalk.Repository.chatroom;

import com.toro.sloppyTalk.domain.ChatRoom;
import com.toro.sloppyTalk.domain.Member;
import com.toro.sloppyTalk.domain.MemberChatRoom;

import java.util.List;

public interface ChatRoomRepository {
    Long save(ChatRoom chatRoom);

    ChatRoom findById(Long chatRoomId);



}
