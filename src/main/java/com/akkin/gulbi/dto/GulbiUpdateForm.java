package com.akkin.gulbi.dto;

import com.akkin.gulbi.domain.Category;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.member.Member;
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

    public Gulbi dtoToEntity(Member member) {
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
