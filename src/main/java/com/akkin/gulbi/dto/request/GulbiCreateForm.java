package com.akkin.gulbi.dto.request;

import com.akkin.gulbi.domain.GulbiCategory;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "사용자가 등록하려는 아낀 정보")
@Getter
public class GulbiCreateForm {

    @Schema(description = "아낀 년도")
    private Integer year;

    @Schema(description = "아낀 월")
    private Integer month;

    @Schema(description = "아낀 일")
    private Integer day;

    @Schema(description = "이미지 링크")
    private String imageUrl;

    @Schema(description = "카테고리")
    private GulbiCategory category;

    @Schema(description = "소비 내용")
    private String saveContent;

    @Schema(description = "어떻게 아꼈는지")
    private String how;

    @Schema(description = "예상 비용")
    private Integer expectCost;

    @Schema(description = "실제 비용")
    private Integer realCost;

    public Gulbi dtoToEntity(final Member member) {
        return Gulbi.builder()
            .member(member)
            .year(year)
            .month(month)
            .day(day)
            .imageUrl(imageUrl)
            .category(category)
            .saveContent(saveContent)
            .how(how)
            .expectCost(expectCost)
            .realCost(realCost)
            .build();
    }

    public GulbiCreateForm( final Integer year,
                            final Integer month,
                            final Integer day,
                            final String imageUrl,
                            final GulbiCategory category,
                            final String saveContent,
                            final String how,
                            final Integer expectCost,
                            final Integer realCost) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.imageUrl = imageUrl;
        this.category = category;
        this.saveContent = saveContent;
        this.how = how;
        this.expectCost = expectCost;
        this.realCost = realCost;
    }
}
