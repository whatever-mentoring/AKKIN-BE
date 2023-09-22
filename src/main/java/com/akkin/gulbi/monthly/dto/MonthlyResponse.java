package com.akkin.gulbi.monthly.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MonthlyResponse {

    private Integer dining;

    private Integer traffic;

    private Integer shopping;

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
