package com.akkin.gulbi.weekly.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class WeeklyGulbiEntries {

    List<WeeklyGulbiEntry> entries;

    public WeeklyGulbiEntries(List<WeeklyGulbiEntry> entries) {
        this.entries = entries;
    }
}
