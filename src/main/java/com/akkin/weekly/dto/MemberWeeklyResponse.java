package com.akkin.weekly.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "주간 아낀 항목 정보")
@Getter
public class MemberWeeklyResponse {

    @Schema(description = "서버 시간 기준 월")
    private final Integer month;

    private final List<WeeklyGulbiEntry> entries;

    @Builder
    public MemberWeeklyResponse(final Integer month,
                                final List<WeeklyGulbiEntry> weeklyGulbiEntries) {
        this.month = month;
        this.entries = weeklyGulbiEntries;
    }
}
