package com.akkin.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthToken {

    private String accessToken;

    private String refreshToken;

    public AuthToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
