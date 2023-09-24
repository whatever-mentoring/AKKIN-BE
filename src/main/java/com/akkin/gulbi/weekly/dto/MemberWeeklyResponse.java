package com.akkin.gulbi.weekly.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "주간 아낀 항목 정보")
@Setter
@Getter
public class MemberWeeklyResponse {

    @Schema(description = "서버 시간 기준 월")
    Integer month;

    @Schema(description = "서버 시간 기준 월의 몇 번째 주인지")
    Integer weekOfMonth;

    WeeklyGulbiEntries entries;

    @Builder
    public MemberWeeklyResponse(Integer month, Integer weekOfMonth, WeeklyGulbiEntries entries) {
        this.month = month;
        this.weekOfMonth = weekOfMonth;
        this.entries = entries;
    }
}
