package com.akkin.fixture;

import com.akkin.gulbi.domain.Category;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.gulbi.dto.request.GulbiCreateForm;
import com.akkin.member.domain.Member;

@SuppressWarnings("NonAsciiCharacters")
public class GulbiFixture {

    public static Gulbi 아낀_항목_만들기(Member member, int year, int month, int day,
        Category category, String content, String how, int expectCost, int realCost) {
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

    public static Gulbi 식비_500원_아낀_항목_만들기(Member member, int year, int month, int day) {
        return Gulbi.builder()
            .member(member)
            .year(year)
            .month(month)
            .day(day)
            .category(Category.DINING)
            .content("DINING_content")
            .how("DINING_how")
            .expectCost(1000)
            .realCost(500)
            .build();
    }

    public static Gulbi 교통_500원_아낀_항목_만들기(Member member, int year, int month, int day) {
        return Gulbi.builder()
            .member(member)
            .year(year)
            .month(month)
            .day(day)
            .category(Category.TRAFFIC)
            .content("TRAFFIC_content")
            .how("TRAFFIC_how")
            .expectCost(1000)
            .realCost(500)
            .build();
    }

    public static Gulbi 쇼핑_500원_아낀_항목_만들기(Member member, int year, int month, int day) {
        return Gulbi.builder()
            .member(member)
            .year(year)
            .month(month)
            .day(day)
            .category(Category.SHOPPING)
            .content("SHOPPING_content")
            .how("SHOPPING_how")
            .expectCost(1000)
            .realCost(500)
            .build();
    }

    public static Gulbi 기타_500원_아낀_항목_만들기(Member member, int year, int month, int day) {
        return Gulbi.builder()
            .member(member)
            .year(year)
            .month(month)
            .day(day)
            .category(Category.ETC)
            .content("ETC_content")
            .how("ETC_how")
            .expectCost(1000)
            .realCost(500)
            .build();
    }

    public static GulbiCreateForm 아낀_항목_생성_폼_만들기(Category category) {
        return new GulbiCreateForm(2023, 10, 9, category, "content", "how", 1000, 500);
    }
}
