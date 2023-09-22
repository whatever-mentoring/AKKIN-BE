package com.akkin.gulbi.monthly.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonthRanking {

    private Integer firstDay;
    private Integer firstAmount;

    private Integer secondDay;
    private Integer secondAmount;

    private Integer thirdDay;
    private Integer thirdAmount;

    @Builder
    public MonthRanking(Integer firstDay, Integer firstAmount, Integer secondDay, Integer secondAmount, Integer thirdDay, Integer thirdAmount) {
        this.firstDay = firstDay;
        this.firstAmount = firstAmount;
        this.secondDay = secondDay;
        this.secondAmount = secondAmount;
        this.thirdDay = thirdDay;
        this.thirdAmount = thirdAmount;
    }
}
