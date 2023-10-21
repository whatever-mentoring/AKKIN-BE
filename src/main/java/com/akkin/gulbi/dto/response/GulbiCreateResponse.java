package com.akkin.gulbi.dto.response;

import com.akkin.gulbi.domain.Category;
import com.akkin.gulbi.domain.Gulbi;
import lombok.Getter;

@Getter
public class GulbiCreateResponse {

    private final Long id;

    private final Integer year;

    private final Integer month;

    private final Integer day;

    private final Category category;

    private final String saveContent;

    private final String how;

    private final Integer expectCost;

    private final Integer realCost;

    public GulbiCreateResponse(final Gulbi gulbi) {
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
