package com.akkin.login.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthMember {

    private Long id;

    private String name;

    private String email;

    public AuthMember(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
