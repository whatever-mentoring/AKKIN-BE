package com.akkin.login.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class OauthMemberInfo {

    private String name;

    private String email;

    public OauthMemberInfo(Map<String, String> payloadMap) {
        this.name = payloadMap.get("name");
        this.email = payloadMap.get("email");
    }
}
