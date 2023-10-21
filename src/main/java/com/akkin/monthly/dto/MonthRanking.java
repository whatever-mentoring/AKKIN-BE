package com.akkin.monthly.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MonthRanking {

    @Schema(description = "가장 많이 아낀 날")
    private final Integer firstDay;

    @Schema(description = "가장 많이 아낀 금액 합")
    private final Integer firstAmount;

    @Schema(description = "2번쨰로 많이 아낀 날")
    private final Integer secondDay;

    @Schema(description = "2번쨰로 많이 아낀 금액 합")
    private final Integer secondAmount;

    @Schema(description = "3번쨰로 많이 아낀 날")
    private final Integer thirdDay;

    @Schema(description = "3번쨰로 많이 아낀 금액 합")
    private final Integer thirdAmount;

    @Builder
    public MonthRanking(final Integer firstDay,
                        final Integer firstAmount,
                        final Integer secondDay,
                        final Integer secondAmount,
                        final Integer thirdDay,
                        final Integer thirdAmount) {
        this.firstDay = firstDay;
        this.firstAmount = firstAmount;
        this.secondDay = secondDay;
        this.secondAmount = secondAmount;
        this.thirdDay = thirdDay;
        this.thirdAmount = thirdAmount;
    }
}
