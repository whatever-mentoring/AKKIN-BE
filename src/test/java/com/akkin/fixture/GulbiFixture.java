package com.akkin.fixture;

import com.akkin.gulbi.domain.GulbiCategory;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.gulbi.dto.request.GulbiCreateForm;
import com.akkin.gulbi.dto.request.GulbiUpdateForm;
import com.akkin.member.domain.Member;

@SuppressWarnings("NonAsciiCharacters")
public class GulbiFixture {

    public static Gulbi 아낀_항목_만들기(Member member, int year, int month, int day,
        GulbiCategory gulbiCategory, String content, String how, int expectCost, int realCost) {
        return Gulbi.builder()
            .member(member)
            .imageUrl("image")
            .year(year)
            .month(month)
            .day(day)
            .category(gulbiCategory)
            .saveContent(content)
            .how(how)
            .expectCost(expectCost)
            .realCost(realCost)
            .build();
    }

    public static Gulbi 식비_500원_아낀_항목_만들기(Member member, int year, int month, int day) {
        return Gulbi.builder()
            .member(member)
            .imageUrl("image")
            .year(year)
            .month(month)
            .day(day)
            .category(GulbiCategory.DINING)
            .saveContent("DINING_content")
            .how("DINING_how")
            .expectCost(1000)
            .realCost(500)
            .build();
    }

    public static Gulbi 교통_500원_아낀_항목_만들기(Member member, int year, int month, int day) {
        return Gulbi.builder()
            .member(member)
            .imageUrl("image")
            .year(year)
            .month(month)
            .day(day)
            .category(GulbiCategory.TRAFFIC)
            .saveContent("TRAFFIC_content")
            .how("TRAFFIC_how")
            .expectCost(1000)
            .realCost(500)
            .build();
    }

    public static Gulbi 쇼핑_500원_아낀_항목_만들기(Member member, int year, int month, int day) {
        return Gulbi.builder()
            .member(member)
            .imageUrl("image")
            .year(year)
            .month(month)
            .day(day)
            .category(GulbiCategory.SHOPPING)
            .saveContent("SHOPPING_content")
            .how("SHOPPING_how")
            .expectCost(1000)
            .realCost(500)
            .build();
    }

    public static Gulbi 기타_500원_아낀_항목_만들기(Member member, int year, int month, int day) {
        return Gulbi.builder()
            .member(member)
            .imageUrl("image")
            .year(year)
            .month(month)
            .day(day)
            .category(GulbiCategory.ETC)
            .saveContent("ETC_content")
            .how("ETC_how")
            .expectCost(1000)
            .realCost(500)
            .build();
    }

    public static GulbiCreateForm 아낀_항목_생성_폼_만들기(GulbiCategory gulbiCategory) {
        return new GulbiCreateForm(2023, 10, 9, "imageUrl", gulbiCategory, "content", "how", 1000, 500);
    }

    public static GulbiUpdateForm 아낀_날짜_수정_폼_만들기(Gulbi gulbi, int year, int month, int day) {
        return new GulbiUpdateForm( year,
                                    month,
                                    day,
                                    gulbi.getImageUrl(),
                                    gulbi.getCategory(),
                                    gulbi.getSaveContent(),
                                    gulbi.getHow(),
                                    gulbi.getExpectCost(),
                                    gulbi.getRealCost()
        );
    }
}
