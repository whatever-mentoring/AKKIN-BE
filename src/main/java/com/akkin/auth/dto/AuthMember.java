package com.akkin.auth.dto;

import com.akkin.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class AuthMember {

    private Long id;

    private String name;

    private String email;

    private LocalDateTime createdAt;

    public AuthMember(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.createdAt = LocalDateTime.now();
    }
}
