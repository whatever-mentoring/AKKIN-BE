package com.akkin.gulbi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "아낀 항목들의 리스트 반환")
@Setter
@Getter
public class GulbiResponses {

    List<GulbiCreateResponse> entries;

    public GulbiResponses(List<GulbiCreateResponse> entries) {
        this.entries = entries;
    }
}
