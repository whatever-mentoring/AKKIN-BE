package com.akkin.gulbi.monthly.dto;

import com.akkin.gulbi.Gulbi;
import com.akkin.gulbi.weekly.dto.WeeklyGulbiEntry;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AkkinEntry {

    private Long id;

    private String saveContent;

    private String how;

    @Builder
    public AkkinEntry(Gulbi gulbi) {
        this.id = gulbi.getId();
        this.saveContent = gulbi.getContent();
        this.how = gulbi.getHow();
    }
}
