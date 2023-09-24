package com.akkin.gulbi.monthly.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonthRanking {

    @Schema(description = "가장 많이 아낀 날")
    private Integer firstDay;

    @Schema(description = "가장 많이 아낀 금액 합")
    private Integer firstAmount;

    @Schema(description = "2번쨰로 많이 아낀 날")
    private Integer secondDay;

    @Schema(description = "2번쨰로 많이 아낀 금액 합")
    private Integer secondAmount;

    @Schema(description = "3번쨰로 많이 아낀 날")
    private Integer thirdDay;

    @Schema(description = "3번쨰로 많이 아낀 금액 합")
    private Integer thirdAmount;

    @Builder
    public MonthRanking(Integer firstDay, Integer firstAmount, Integer secondDay,
        Integer secondAmount, Integer thirdDay, Integer thirdAmount) {
        this.firstDay = firstDay;
        this.firstAmount = firstAmount;
        this.secondDay = secondDay;
        this.secondAmount = secondAmount;
        this.thirdDay = thirdDay;
        this.thirdAmount = thirdAmount;
    }
}
