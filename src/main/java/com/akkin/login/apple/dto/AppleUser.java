package com.akkin.login.apple.dto;

import lombok.Getter;

@Getter
public class AppleUser {

    private String name;

    private String socialId;

    private String email;

    public AppleUser(String name, String socialId, String email) {
        this.name = name;
        this.socialId = socialId;
        this.email = email;
    }

    public AppleUser(String socialId, String email) {
        this.socialId = socialId;
        this.email = email;
    }
}
