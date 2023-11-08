package com.toro.sloppyTalk.api;

import com.toro.sloppyTalk.domain.Member;
import com.toro.sloppyTalk.login.SessionManagerImpl;
import com.toro.sloppyTalk.service.member.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Transactional(readOnly = true)
@CrossOrigin(origins = "http://localhost:19006")
public class MemberController {

    private final MemberService memberService;
    private final SessionManagerImpl sessionManager;

    @Transactional(readOnly = false)
    @PostMapping("/new")
    public void join(@RequestBody RegisterDto param){

        Member member = getMember(param);
        memberService.save(member);

    }

    @GetMapping("/{sessionId}")
    public List<MembersResponseDto> getMembers(@PathVariable String sessionId){
        Long userId = sessionManager.getMemberId(sessionId);
        List<Member> members = memberService.findMembers(userId);
        return members.stream().map(MembersResponseDto::new).collect(Collectors.toList());
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

    @Data
    static class MembersResponseDto{
        private String memberName;
        private Long memberId;

        public MembersResponseDto(Member m) {
            this.memberName = m.getName();
            this.memberId = m.getId();
        }
    }
}
