package com.akkin.login;

import com.akkin.auth.RedisService;
import com.akkin.auth.dto.AuthToken;
import com.akkin.login.apple.AppleOauthService;
import com.akkin.login.apple.dto.AppleUser;
import com.akkin.login.dto.request.AppleLoginRequest;
import com.akkin.login.dto.AuthMember;
import com.akkin.member.Member;
import com.akkin.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final RedisService redisService;

    private final MemberService memberService;

    private final AppleOauthService appleOauthService;

    @PostMapping("/oauth2/apple")
    public ResponseEntity<Void> appleOauthLogin(@RequestBody AppleLoginRequest appleLoginRequest) {
        AppleUser appleUser = appleOauthService.createAppleUser(appleLoginRequest.getAppleToken());
        Member member = memberService.saveOrUpdateMember(appleUser);
        AuthMember authMember = new AuthMember(member);
        AuthToken authToken = redisService.issueAuthToken(authMember);

        HttpHeaders headers = makeAuthHeader(authToken);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @GetMapping("/dummy/{id}")
    public ResponseEntity<Void> demoOauthLogin(@PathVariable("id") Long id) throws Exception {
        Member member = memberService.findMemberOrElseThrow(id);
        AuthMember authMember = new AuthMember(member);
        AuthToken authToken = redisService.issueAuthToken(authMember);

        HttpHeaders headers = makeAuthHeader(authToken);
        return ResponseEntity.ok().headers(headers).build();
    }

    private HttpHeaders makeAuthHeader(AuthToken authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("accessToken", authToken.getAccessToken());
        headers.add("refreshToken", authToken.getRefreshToken());
        return headers;
    }
}
