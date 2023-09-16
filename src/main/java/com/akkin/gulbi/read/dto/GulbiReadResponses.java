package com.akkin.gulbi.read.dto;

import java.util.List;

import com.akkin.gulbi.create.dto.GulbiCreateResponse;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GulbiReadResponses {

    List<GulbiCreateResponse> entries;

    public GulbiReadResponses(List<GulbiCreateResponse> entries) {
        this.entries = entries;
    }
}
