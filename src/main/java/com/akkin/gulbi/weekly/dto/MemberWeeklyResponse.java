package com.akkin.gulbi.weekly.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberWeeklyResponse {

    Integer month;

    Integer weekOfMonth;

    WeeklyGulbiEntries entries;

    @Builder
    public MemberWeeklyResponse(Integer month, Integer weekOfMonth, WeeklyGulbiEntries entries) {
        this.month = month;
        this.weekOfMonth = weekOfMonth;
        this.entries = entries;
    }
}
