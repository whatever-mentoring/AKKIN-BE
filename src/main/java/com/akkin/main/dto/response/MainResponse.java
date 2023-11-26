package com.akkin.main.dto.response;

import com.akkin.gulbi.dto.response.GulbiListResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "사용자가 입력한 아낀 항목 정보")
@Getter
@NoArgsConstructor
public class MainResponse {

    @Schema(description = "오늘 등록한 아낀 항목들")
    private GulbiListResponse today;

    @Schema(description = "그동안 기록한 아낀 항목들")
    private GulbiListResponse firstPage;

    public MainResponse(GulbiListResponse today, GulbiListResponse firstPage) {
        this.today = today;
        this.firstPage = firstPage;
    }
}
