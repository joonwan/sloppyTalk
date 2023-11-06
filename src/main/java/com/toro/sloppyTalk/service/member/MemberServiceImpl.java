package com.toro.sloppyTalk.service.member;

import com.toro.sloppyTalk.Repository.member.MemberRepository;
import com.toro.sloppyTalk.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Transactional(readOnly = false)
    @Override
    public Long save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
