package com.akkin.gulbi.monthly.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MonthlyResponse {

    @Schema(description = "월간 아낀 식비 총합")
    private Integer dining;

    @Schema(description = "월간 아낀 교통비 총합")
    private Integer traffic;

    @Schema(description = "월간 아낀 쇼핑비 총합")
    private Integer shopping;

    @Schema(description = "월간 아낀 기타 총합")
    private Integer etc;

    private MonthRanking monthRanking;


    @Builder
    public MonthlyResponse(Integer dining, Integer traffic, Integer shopping, Integer etc, MonthRanking monthRanking) {
        this.dining = dining;
        this.traffic = traffic;
        this.shopping = shopping;
        this.etc = etc;
        this.monthRanking = monthRanking;
    }
}
