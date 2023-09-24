package com.akkin.login.dto;

import com.akkin.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
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
