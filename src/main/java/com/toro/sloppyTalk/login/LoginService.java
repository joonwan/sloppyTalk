package com.toro.sloppyTalk.login;

import com.toro.sloppyTalk.Repository.member.MemberRepository;
import com.toro.sloppyTalk.domain.Member;
import com.toro.sloppyTalk.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberService memberService;

    public boolean isMember(String loginId, String password){
        Member findMember = memberService.findLoginMember(loginId);
        log.info("{} = {} == {}", findMember.getPassword(), password, findMember.getPassword().equals(password));
        return findMember.getPassword().equals(password);
    }
}
