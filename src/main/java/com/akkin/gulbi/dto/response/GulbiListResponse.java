package com.akkin.gulbi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "아낀 항목들의 리스트 반환")
@Setter
@Getter
public class GulbiListResponse {

    private final List<GulbiResponse> entries;

    private final long lastId;

    public GulbiListResponse(final List<GulbiResponse> entries, final long lastId) {
        this.entries = entries;
        this.lastId = lastId;
    }
}
