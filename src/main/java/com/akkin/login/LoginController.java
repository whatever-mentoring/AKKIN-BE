package com.akkin.login;

import com.akkin.auth.RedisService;
import com.akkin.auth.dto.AuthToken;
import com.akkin.login.apple.AppleOauthService;
import com.akkin.login.apple.dto.AppleUser;
import com.akkin.login.dto.AuthMember;
import com.akkin.login.dto.request.AppleLoginRequest;
import com.akkin.member.Member;
import com.akkin.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/api")
public class LoginController {

    private final RedisService redisService;

    private final MemberService memberService;

    private final AppleOauthService appleOauthService;

    @Operation(summary = "애플 로그인", description = "클라이언트가 로그인 후 받은 토큰을 공개키로 파싱")
    @PostMapping("/login/oauth2/apple")
    public ResponseEntity<Void> appleOauthLogin(@RequestBody AppleLoginRequest appleLoginRequest) {
        AppleUser appleUser = appleOauthService.createAppleUser(appleLoginRequest.getAppleToken());
        Member member = memberService.saveOrUpdateMember(appleUser);
        AuthMember authMember = new AuthMember(member);
        AuthToken authToken = redisService.issueAuthToken(authMember);

        HttpHeaders headers = makeAuthHeader(authToken);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @Operation(summary = "더미 유저 로그인", description = "테스트용 데이터")
    @GetMapping("/login/dummy/{id}")
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

    @Operation(summary = "로그아웃", parameters = {
        @Parameter(
            in = ParameterIn.HEADER,
            name = "accessToken",
            required = false,
            schema = @Schema(type = "string"),
            description = "Access Token")
    }, description = "해당 API를 호출하면 결과에 상관없이 200이 반환됩니다.")
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) throws Exception {
        String accessToken = request.getHeader("accessToken");
        redisService.deleteAuthToken(accessToken);
        return ResponseEntity.ok().build();
    }
}
