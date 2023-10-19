package com.akkin.auth.apple.dto;

import lombok.Getter;

@Getter
public class AppleUser {

    private String name;

    private String email;

    public AppleUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public AppleUser(String email) {
        this.name = "apple";
        this.email = email;
    }
}
