package com.akkin.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AppleRevokeRequest {

    @Schema(description = "클라이언트에서 애플 로그인을 수행하고 받은 JWT 형태의 토큰")
    private final String appleToken;

    @Schema(description = "클라이언트에서 애플 로그인을 수행하고 받은 인증 토큰")
    private final String authorizationCode;

    @JsonCreator
    public AppleRevokeRequest(@JsonProperty("appleToken")final String appleToken,
                              @JsonProperty("authorizationCode")final String authorizationCode) {
        this.appleToken = appleToken;
        this.authorizationCode = authorizationCode;
    }

}
