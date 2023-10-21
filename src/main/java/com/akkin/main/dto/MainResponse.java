package com.akkin.main.dto;

import com.akkin.monthly.dto.AkkinEntry;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MainResponse {

    @Schema(description = "서버 기준 월")
    private final Integer month;

    @Schema(description = "서버 기준 달의 몇째 주")
    private final Integer weekOfMonth;

    @Schema(description = "주간 아낀 비용 총합")
    private final Integer weeklyTotalCost;

    @Schema(description = "월간 아낀 식비 총합")
    private final Integer monthlyDining;

    @Schema(description = "월간 아낀 교통비 총합")
    private final Integer monthlyTraffic;

    @Schema(description = "월간 아낀 쇼핑비 총합")
    private final Integer monthlyShopping;

    @Schema(description = "월간 아낀 기타 총합")
    private final Integer monthlyEtc;

    @Schema(description = "월간 아낀 비용 총합")
    private final Integer monthlyTotalCost;

    private final List<AkkinEntry> entries;

    @Builder
    public MainResponse(final Integer month,
                        final Integer weekOfMonth,
                        final Integer weeklyTotalCost,
                        final Integer monthlyDining,
                        final Integer monthlyTraffic,
                        final Integer monthlyShopping,
                        final Integer monthlyEtc,
                        final List<AkkinEntry> entries) {
        this.month = month;
        this.weekOfMonth = weekOfMonth;
        this.weeklyTotalCost = weeklyTotalCost;
        this.monthlyDining = monthlyDining;
        this.monthlyTraffic = monthlyTraffic;
        this.monthlyShopping = monthlyShopping;
        this.monthlyEtc = monthlyEtc;
        this.monthlyTotalCost = monthlyDining + monthlyTraffic + monthlyShopping + monthlyEtc;
        this.entries = entries;
    }
}
