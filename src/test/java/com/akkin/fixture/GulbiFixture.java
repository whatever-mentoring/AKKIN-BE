package com.akkin.fixture;

import com.akkin.gulbi.Category;
import com.akkin.gulbi.Gulbi;
import com.akkin.member.Member;

@SuppressWarnings("NonAsciiCharacters")
public class GulbiFixture {

    public static Gulbi 굴비_만들기(Member member, Integer year, Integer month, Integer day,
        Category category, String content, String how, Integer expectCost, Integer realCost) {
        return Gulbi.builder()
            .member(member)
            .year(year)
            .month(month)
            .day(day)
            .category(category)
            .content(content)
            .how(how)
            .expectCost(expectCost)
            .realCost(realCost)
            .build();
    }

}
