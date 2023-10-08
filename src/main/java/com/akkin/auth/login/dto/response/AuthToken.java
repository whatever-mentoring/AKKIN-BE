package com.akkin.auth.login.dto.response;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthToken {

    private String accessToken;

    private String refreshToken;

    public AuthToken() {
        this.accessToken = UUID.randomUUID().toString();
        this.refreshToken = UUID.randomUUID().toString();
    }
}
