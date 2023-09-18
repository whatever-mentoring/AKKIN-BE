package com.akkin.weekly.dto;

import com.akkin.gulbi.Gulbi;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WeeklyGulbiEntry {

    private Long id;

    private Integer saveCost;

    public String saveContent;

    public Integer day;

    public WeeklyGulbiEntry(Gulbi gulbi) {
        this.id = gulbi.getId();
        this.saveCost = gulbi.getSaveMoney();
        this.saveContent = gulbi.getContent();
        this.day = gulbi.getSaveDay();
    }
}
