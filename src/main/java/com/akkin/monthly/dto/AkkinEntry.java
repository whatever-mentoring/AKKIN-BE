package com.akkin.monthly.dto;

import com.akkin.gulbi.domain.Gulbi;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AkkinEntry {

    private final Long id;

    private final String saveContent;

    private final String how;

    @Builder
    public AkkinEntry(final Gulbi gulbi) {
        this.id = gulbi.getId();
        this.saveContent = gulbi.getContent();
        this.how = gulbi.getHow();
    }
}
