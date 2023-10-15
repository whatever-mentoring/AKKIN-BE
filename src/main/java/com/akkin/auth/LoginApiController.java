package com.akkin.auth;

import static com.akkin.auth.token.AuthTokenService.accessTokenMap;

import com.akkin.auth.apple.AppleOauthService;
import com.akkin.auth.apple.dto.AppleUser;
import com.akkin.auth.dto.request.AppleLoginRequest;
import com.akkin.auth.token.AuthToken;
import com.akkin.auth.token.AuthTokenService;
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
public class LoginApiController {

    private final AuthTokenService authTokenService;

    private final MemberService memberService;

    private final AppleOauthService appleOauthService;

    public static final String ACCESS_TOKEN_HEADER = "accessToken";
    public static final String REFRESH_TOKEN_HEADER = "refreshToken";

    @Operation(summary = "애플 로그인", description = "클라이언트가 로그인 후 받은 토큰을 공개키로 파싱")
    @PostMapping("/login/oauth2/apple")
    public ResponseEntity<Void> appleOauthLogin(@RequestBody AppleLoginRequest appleLoginRequest) {
        AppleUser appleUser = appleOauthService.createAppleUser(appleLoginRequest.getAppleToken());
        Member member = memberService.saveOrUpdateMember(appleUser);
        AuthToken authToken = authTokenService.issue(member);
        HttpHeaders headers = makeAuthHeader(authToken);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @Operation(summary = "더미 유저 로그인", description = "테스트용 데이터")
    @GetMapping("/login/dummy/{id}")
    public ResponseEntity<Void> demoOauthLogin(@PathVariable("id") Long id) throws Exception {
        Member member = memberService.findMember(id);
        AuthToken authToken = authTokenService.issue(member);
        HttpHeaders headers = makeAuthHeader(authToken);
        return ResponseEntity.ok().headers(headers).build();
    }

    private HttpHeaders makeAuthHeader(AuthToken authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ACCESS_TOKEN_HEADER, authToken.getAccessToken());
        headers.add(REFRESH_TOKEN_HEADER, authToken.getRefreshToken());
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
        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
        accessTokenMap.remove(accessToken);
        return ResponseEntity.ok().build();
    }
}