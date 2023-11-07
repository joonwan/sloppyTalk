package com.toro.sloppyTalk.login;

import com.toro.sloppyTalk.domain.Member;
import com.toro.sloppyTalk.service.member.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:19006")
@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MemberService memberService;
    private final SessionManagerImpl sessionManager;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto param){
        String loginId = param.getLoginId();
        String password = param.getPassword();
        String sessionId = param.getSessionId();

        log.info("logindId = {}, password = {}, sessionId = {}", loginId,password,sessionId);

        if(loginService.isMember(loginId, password)){
            boolean isLogin = true;
            Member member = memberService.findLoginMember(loginId);
            Long memberId = member.getId();
            if(sessionId != null){
                log.info("sessionId already exist!!");
                return new LoginResponseDto(memberId, sessionId,isLogin);
            }
            log.info("create sessionId!!");
            String newSessionId = sessionManager.createSession(member);
            return new LoginResponseDto(memberId, newSessionId,isLogin);
        }
        return new LoginResponseDto(null, null,false);
    }

    @Data
    static class LoginRequestDto{

        private String loginId;
        private String password;
        private String sessionId;
    }

    @Data
    static class LoginResponseDto{
        private Long memberId;
        private String sessionId;
        private boolean isLogin;

        public LoginResponseDto(Long memberId, String sessionId, boolean isLogin) {
            this.memberId = memberId;
            this.sessionId = sessionId;
            this.isLogin = isLogin;
        }
    }
}
