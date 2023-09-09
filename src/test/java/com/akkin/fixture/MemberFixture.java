package com.akkin.fixture;

import com.akkin.member.Member;

@SuppressWarnings("NonAsciiCharacters")
public class MemberFixture {

    public static Member 회원_만들기(String name, String email) {
        return new Member(name, email);
    }
}
