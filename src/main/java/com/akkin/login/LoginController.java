package com.akkin.login;

import com.akkin.auth.RedisService;
import com.akkin.auth.dto.AuthToken;
import com.akkin.login.dto.AuthMember;
import com.akkin.login.dto.OauthMemberInfo;
import com.akkin.member.Member;
import com.akkin.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final RedisService redisService;

    private final MemberService memberService;

    private final OauthService oauthService;

    @GetMapping("/oauth2/apple")
    public ResponseEntity<Void> appleOauthLogin(@RequestParam("code") String code) throws Exception {
        oauthService.authCode(code);
//        Member member = memberService.saveOrUpdateMember(oauthMemberInfo.getName(), oauthMemberInfo.getEmail());
//        AuthMember authMember = new AuthMember(member);
//        AuthToken authToken = redisService.issueAuthToken(authMember);
//
//        HttpHeaders headers = makeAuthHeader(authToken);
//        return new ResponseEntity<>(headers, HttpStatus.OK);
        return ResponseEntity.ok().build();
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
