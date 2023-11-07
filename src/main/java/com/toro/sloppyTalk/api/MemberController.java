package com.toro.sloppyTalk.api;

import com.toro.sloppyTalk.domain.Member;
import com.toro.sloppyTalk.service.member.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Transactional(readOnly = true)
@CrossOrigin(origins = "http://localhost:19006")
public class MemberController {

    private final MemberService memberService;

    @Transactional(readOnly = false)
    @PostMapping("/new")
    public void join(@RequestBody RegisterDto param){

        Member member = getMember(param);
        memberService.save(member);

    }

    private static Member getMember(RegisterDto param) {
        String name = param.getName();
        String loginId = param.getLoginId();
        String password = param.getPassword();

        return new Member(name,loginId,password);
    }

    @Data
    static class RegisterDto{

        private String name;
        private String loginId;
        private String password;
    }
}
