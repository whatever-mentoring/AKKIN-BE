package com.akkin.gulbi.create.dto;

import com.akkin.gulbi.Category;
import com.akkin.gulbi.Gulbi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "서버에 저장된 아낀 정보")
@Getter
@NoArgsConstructor
public class GulbiCreateResponse {

    @Schema(description = "아낀 항목 식별자")
    private Long id;

    private Integer year;

    private Integer month;

    private Integer day;

    private Category category;

    private String saveContent;

    private String how;

    private Integer expectCost;

    private Integer realCost;

    public GulbiCreateResponse(Gulbi gulbi) {
        this.id = gulbi.getId();
        this.year = gulbi.getSaveYear();
        this.month = gulbi.getSaveMonth();
        this.day = gulbi.getSaveDay();
        this.category = gulbi.getCategory();
        this.saveContent = gulbi.getContent();
        this.how = gulbi.getHow();
        this.expectCost = gulbi.getExpectCost();
        this.realCost = gulbi.getRealCost();
    }
}
