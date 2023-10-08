package com.akkin.auth.login.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppleLoginRequest {

    @Schema(description = "클라이언트에서 애플 로그인을 수행하고 받은 JWT 형태의 토큰")
    private String appleToken;
}
