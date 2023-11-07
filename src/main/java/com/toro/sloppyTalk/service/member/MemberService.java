package com.toro.sloppyTalk.service.member;

import com.toro.sloppyTalk.domain.Member;

public interface MemberService {

    Long save(Member member);

    Member findMember(Long memberId);

    Member findLoginMember(String loginId);
}
