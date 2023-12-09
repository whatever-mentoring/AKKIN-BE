package com.akkin.fixture;

import com.akkin.member.domain.Member;

@SuppressWarnings("NonAsciiCharacters")
public class MemberFixture {

    public static Member 회원1_만들기() {
        return new Member("testName1", "testEmail1");
    }

    public static Member 회원2_만들기() {
        return new Member("testName2", "testEmail2");
    }
}
