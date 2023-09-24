package com.akkin.gulbi.weekly.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class WeeklyGulbiEntries {

    List<WeeklyGulbiEntry> entries;

    public WeeklyGulbiEntries(List<WeeklyGulbiEntry> entries) {
        this.entries = entries;
    }
}
