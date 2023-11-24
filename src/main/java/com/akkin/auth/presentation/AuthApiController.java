package com.akkin.auth.presentation;

import com.akkin.auth.apple.AppleOauthService;
import com.akkin.auth.apple.AppleRevokeService;
import com.akkin.auth.apple.AppleTokenService;
import com.akkin.auth.apple.dto.AppleTokenResponse;
import com.akkin.auth.apple.dto.AppleUser;
import com.akkin.auth.dto.request.AppleLoginRequest;
import com.akkin.auth.dto.request.AppleRevokeRequest;
import com.akkin.auth.dto.response.TokenResponse;
import com.akkin.auth.domain.AuthToken;
import com.akkin.auth.application.AuthTokenService;
import com.akkin.member.domain.Member;
import com.akkin.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.akkin.auth.aop.AuthAspect.ACCESS_TOKEN_HEADER;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthApiController implements AuthApiControllerDocs {

    private final AuthTokenService authTokenService;

    private final MemberService memberService;

    private final AppleOauthService appleOauthService;

    private final AppleTokenService appleTokenService;

    private final AppleRevokeService appleRevokeService;

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
        appleOauthService.createAppleUser(appleRevokeRequest.getAppleToken());
        AppleTokenResponse appleToken = appleTokenService.getAppleToken(appleRevokeRequest);
        appleRevokeService.revoke(appleToken);
        return ResponseEntity.ok().build();
    }
}
