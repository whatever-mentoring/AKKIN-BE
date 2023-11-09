package com.akkin.gulbi.dto.request;

import com.akkin.gulbi.domain.Category;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "사용자가 수정하려는 아낀 항목 정보")
@Setter
@Getter
public class GulbiUpdateForm {

    private Integer year;

    private Integer month;

    private Integer day;

    private Category category;

    private String content;

    private String how;

    private Integer expectCost;

    private Integer realCost;

    public Gulbi dtoToEntity(final Member member) {
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

    public GulbiUpdateForm( final Integer year,
                            final Integer month,
                            final Integer day,
                            final Category category,
                            final String content,
                            final String how,
                            final Integer expectCost,
                            final Integer realCost) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.category = category;
        this.content = content;
        this.how = how;
        this.expectCost = expectCost;
        this.realCost = realCost;
    }
}
