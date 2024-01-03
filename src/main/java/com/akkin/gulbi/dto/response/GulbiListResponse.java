package com.akkin.gulbi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "아낀 항목들의 리스트 반환")
@Setter
@Getter
public class GulbiListResponse {

    @Schema(description = "아낀 항목 리스트")
    private final List<GulbiResponse> entries;

    @Schema(description = "마지막으로 조회한 아낀 항목 식별자")
    private long lastId = 0L;

    public GulbiListResponse(final List<GulbiResponse> entries) {
        this.entries = Collections.unmodifiableList(entries);
        if (!entries.isEmpty()) {
            lastId = entries.get(entries.size() - 1).getId();
        }
    }
}
