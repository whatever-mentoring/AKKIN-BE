package com.akkin.auth.dto.response;

import com.akkin.auth.domain.AuthToken;
import lombok.Getter;

@Getter
public class TokenResponse {

    private final String accessToken;
    private final String refreshToken;

    public TokenResponse(final AuthToken authToken) {
        this.accessToken = authToken.getAccessToken();
        this.refreshToken = authToken.getRefreshToken();
    }
}
