package com.akkin.login.dto;

import com.akkin.member.Member;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthMember {

    private Long id;

    private String name;

    private String email;

    public AuthMember(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
