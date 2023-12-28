package com.akkin.auth.presentation;

import com.akkin.auth.dto.request.AppleLoginRequest;
import com.akkin.auth.dto.request.AppleRevokeRequest;
import com.akkin.auth.dto.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "auth", description = "인증 관련 API")
public interface AuthApiControllerDocs {

    @Operation(summary = "애플 로그인", description = "클라이언트가 로그인 후 받은 토큰을 공개키로 파싱")
    ResponseEntity<TokenResponse> appleOauthLogin(final AppleLoginRequest appleLoginRequest);

    @Operation(summary = "더미 유저 로그인", description = "테스트용 데이터")
    ResponseEntity<TokenResponse> demoOauthLogin(@PathVariable("id") final Long id);

    @Operation(summary = "로그아웃", description = "해당 API를 호출하면 결과에 상관없이 200이 반환됩니다.")
    @Parameter(in = ParameterIn.HEADER, name = "accessToken", required = false, description = "Access Token")
    @Parameter(in = ParameterIn.HEADER, name = "refreshToken", required = false, description = "Refresh Token")
    ResponseEntity<Void> logout(HttpServletRequest request);

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴")
    ResponseEntity<Void> revoke(@RequestBody final AppleRevokeRequest appleRevokeRequest);
}
