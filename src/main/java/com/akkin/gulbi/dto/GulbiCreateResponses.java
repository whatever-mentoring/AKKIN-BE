package com.akkin.gulbi.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GulbiCreateResponses {

    List<GulbiCreateResponse> entries;

    public GulbiCreateResponses(List<GulbiCreateResponse> entries) {
        this.entries = entries;
    }
}
