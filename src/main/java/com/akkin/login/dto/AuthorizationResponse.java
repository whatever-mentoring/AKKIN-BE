package com.akkin.login.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorizationResponse {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private Integer expires_in;
    private String id_token;
}
