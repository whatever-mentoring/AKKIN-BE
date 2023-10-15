package com.akkin.auth.dto;

import com.akkin.auth.token.AuthToken;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenInfo {

    private String accessToken;

    private String refreshToken;

    public TokenInfo(AuthToken authToken) {
        this.accessToken = authToken.getAccessToken();
        this.refreshToken = authToken.getRefreshToken();
    }
}
