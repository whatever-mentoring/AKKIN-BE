package com.akkin.gulbi.weekly.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonthWeekInfo {

    Integer month;
    Integer week;
    Integer sunday;

    public MonthWeekInfo(Integer month, Integer week, Integer sunday) {
        this.month = month;
        this.week = week;
        this.sunday = sunday;
    }
}
