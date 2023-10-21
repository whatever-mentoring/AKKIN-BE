package com.akkin.gulbi.dto;

import com.akkin.gulbi.domain.Category;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "사용자가 등록하려는 아낀 정보")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GulbiCreateForm {

    @Schema(description = "아낀 년도")
    private Integer year;

    @Schema(description = "아낀 월")
    private Integer month;

    @Schema(description = "아낀 일")
    private Integer day;

    @Schema(description = "카테고리")
    private Category category;

    @Schema(description = "소비 내용")
    private String saveContent;

    @Schema(description = "어떻게 아꼈는지")
    private String how;

    @Schema(description = "예상 비용")
    private Integer expectCost;

    @Schema(description = "실제 비용")
    private Integer realCost;

    public Gulbi dtoToEntity(Member member) {
        return Gulbi.builder()
            .member(member)
            .year(year)
            .month(month)
            .day(day)
            .category(category)
            .content(saveContent)
            .how(how)
            .expectCost(expectCost)
            .realCost(realCost)
            .build();
    }

}
