package com.akkin.monthly.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class MonthlyResponse {

    @Schema(description = "월간 아낀 식비 총합")
    private final Integer dining;

    @Schema(description = "월간 아낀 교통비 총합")
    private final Integer traffic;

    @Schema(description = "월간 아낀 쇼핑비 총합")
    private final Integer shopping;

    @Schema(description = "월간 아낀 기타 총합")
    private final Integer etc;

    private final MonthRanking monthRanking;


    @Builder
    public MonthlyResponse( final Integer dining,
                            final Integer traffic,
                            final Integer shopping,
                            final Integer etc,
                            final MonthRanking monthRanking) {
        this.dining = dining;
        this.traffic = traffic;
        this.shopping = shopping;
        this.etc = etc;
        this.monthRanking = monthRanking;
    }
}
