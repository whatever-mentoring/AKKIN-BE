package com.akkin.weekly.dto;

import com.akkin.gulbi.domain.Gulbi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "주간 아낀 항목 정보")
@Setter
@Getter
public class WeeklyGulbiEntry {

    @Schema(description = "아낀 항목 식별자")
    private Long id;

    @Schema(description = "절약한 비용")
    private Integer saveCost;

    @Schema(description = "소비 내용")
    public String saveContent;

    @Schema(description = "절약한 일(day)")
    public Integer day;

    public WeeklyGulbiEntry(Gulbi gulbi) {
        this.id = gulbi.getId();
        this.saveCost = gulbi.getSaveMoney();
        this.saveContent = gulbi.getContent();
        this.day = gulbi.getSaveDay();
    }
}
