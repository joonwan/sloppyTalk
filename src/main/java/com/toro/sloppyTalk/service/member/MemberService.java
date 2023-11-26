package com.toro.sloppyTalk.service.member;

import com.toro.sloppyTalk.domain.Friend;
import com.toro.sloppyTalk.domain.Member;

import java.util.List;

public interface MemberService {

    Long save(Member member);

    Member findMember(Long memberId);

    Member findLoginMember(String loginId);

    List<Member> findMembers(Long userId);

    Long follow(Long memberId, Long targetId);

    List<Friend> findFriends(Long memberId);



}
