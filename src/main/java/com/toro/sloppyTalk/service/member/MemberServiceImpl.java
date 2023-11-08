package com.toro.sloppyTalk.service.member;

import com.toro.sloppyTalk.Repository.member.MemberRepository;
import com.toro.sloppyTalk.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
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

    @Override
    public Member findLoginMember(String loginId){
        log.info("loginId = {}", loginId);
        return memberRepository.findByLoginId(loginId);
    }

    @Override
    public List<Member> findMembers(Long userId) {
        return memberRepository.findAll(userId);
    }
}
