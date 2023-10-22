package com.akkin.weekly.dto;

import com.akkin.gulbi.domain.Gulbi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "주간 아낀 항목 정보")
@Getter
public class WeeklyGulbiEntry {

    @Schema(description = "아낀 항목 식별자")
    private final Long id;

    @Schema(description = "절약한 비용")
    private final Integer saveCost;

    @Schema(description = "소비 내용")
    public final String saveContent;

    @Schema(description = "절약한 일(day)")
    public final Integer day;

    public WeeklyGulbiEntry(final Gulbi gulbi) {
        this.id = gulbi.getId();
        this.saveCost = gulbi.getSaveMoney();
        this.saveContent = gulbi.getContent();
        this.day = gulbi.getSaveDay();
    }
}
