package com.akkin.gulbi.read.dto;

import com.akkin.gulbi.Category;
import com.akkin.gulbi.Gulbi;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GulbiReadResponse {

    private Long id;

    private Integer year;

    private Integer month;

    private Integer day;

    private Category category;

    private String content;

    private String how;

    private Integer expectCost;

    private Integer realCost;

    public GulbiReadResponse(Gulbi gulbi) {
        this.id = gulbi.getId();
        this.year = gulbi.getSaveYear();
        this.month = gulbi.getSaveMonth();
        this.day = gulbi.getSaveDay();
        this.category = gulbi.getCategory();
        this.content = gulbi.getContent();
        this.how = gulbi.getHow();
        this.expectCost = gulbi.getExpectCost();
        this.realCost = gulbi.getRealCost();
    }
}
