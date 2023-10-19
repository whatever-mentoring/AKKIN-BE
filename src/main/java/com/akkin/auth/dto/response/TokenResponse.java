package com.akkin.auth.dto.response;

import com.akkin.auth.token.AuthToken;
import lombok.Getter;

@Getter
public class TokenResponse {

    private String accessToken;
    private String refreshToken;

    public TokenResponse(AuthToken authToken) {
        this.accessToken = authToken.getAccessToken();
        this.refreshToken = authToken.getRefreshToken();
    }

    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
