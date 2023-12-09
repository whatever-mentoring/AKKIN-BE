package com.akkin.auth.presentation;

import static com.akkin.auth.aop.AuthAspect.ACCESS_TOKEN_HEADER;

import com.akkin.auth.apple.AppleOauthService;
import com.akkin.auth.apple.dto.AppleUser;
import com.akkin.auth.application.AuthTokenService;
import com.akkin.auth.domain.AuthToken;
import com.akkin.auth.dto.request.AppleLoginRequest;
import com.akkin.auth.dto.request.AppleRevokeRequest;
import com.akkin.auth.dto.response.TokenResponse;
import com.akkin.gulbi.application.GulbiService;
import com.akkin.member.application.MemberService;
import com.akkin.member.domain.Member;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthApiController implements AuthApiControllerDocs {

    private final AuthTokenService authTokenService;

    private final MemberService memberService;

    private final AppleOauthService appleOauthService;

    private final GulbiService gulbiService;

    @Override
    @PostMapping("/login/oauth2/apple")
    public ResponseEntity<TokenResponse> appleOauthLogin(@RequestBody final AppleLoginRequest appleLoginRequest) {
        AppleUser appleUser = appleOauthService.createAppleUser(appleLoginRequest.getAppleToken());
        Member member = memberService.saveOrUpdateMember(appleUser);
        AuthToken authToken = authTokenService.issue(member);
        return ResponseEntity.ok(new TokenResponse(authToken));
    }

    @Override
    @GetMapping("/login/dummy/{id}")
    public ResponseEntity<TokenResponse> demoOauthLogin(@PathVariable("id") final Long id) {
        Member member = memberService.findMember(id);
        AuthToken authToken = authTokenService.issue(member);
        return ResponseEntity.ok(new TokenResponse(authToken));
    }

    @Override
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
        authTokenService.deleteAuthToken(accessToken);
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/revoke")
    public ResponseEntity<Void> revoke(@RequestBody final AppleRevokeRequest appleRevokeRequest) {
        AppleUser appleUser = appleOauthService.createAppleUser(appleRevokeRequest.getAppleToken());
        Member member = memberService.findMember(appleUser.getEmail());
        log.info("탈퇴 진행 id: " + member.getId());
        gulbiService.deleteRevokeMemberGulbi(member);
        log.info("관련 아낀 항목 제거 완료");
        authTokenService.deleteRevokedAuthToken(member.getId());
        log.info("인증 정보 제거 완료");
        memberService.deleteMember(member.getId());
        log.info("회원 정보 제거 완료");
        return ResponseEntity.ok().build();
    }
}
