package com.toro.sloppyTalk.Repository.member;

import com.toro.sloppyTalk.domain.Friend;
import com.toro.sloppyTalk.domain.Member;

import java.util.List;

public interface MemberRepository {

    Long save(Member member);

    Member findById(Long memberId);

    Member findByLoginId(String loginId);

    List<Member> findAll(Long userId);
}
