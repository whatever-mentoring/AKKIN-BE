package com.akkin.gulbi.dto.response;

import com.akkin.gulbi.domain.Category;
import com.akkin.gulbi.domain.Gulbi;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "사용자가 입력한 아낀 항목 정보")
@Getter
@NoArgsConstructor
public class GulbiResponse {

    @Schema(description = "아낀 항목 식별자")
    private Long id;

    @Schema(description = "생성 년도")
    private Integer year;

    @Schema(description = "생성 월")
    private Integer month;

    @Schema(description = "생성 일")
    private Integer day;

    @Schema(description = "카테고리(식비, 교통, 쇼핑 ,기타")
    private Category category;

    @Schema(description = "아낀 항목 제목")
    private String saveContent;

    @Schema(description = "아낀 항목 본문")
    private String how;

    @Schema(description = "예상했던 지출")
    private Integer expectCost;

    @Schema(description = "실제 아낀 비용")
    private Integer realCost;

    public GulbiResponse(final Long id,
                         final Integer year,
                         final Integer month,
                         final Integer day,
                         final Category category,
                         final String saveContent,
                         final String how,
                         final Integer expectCost,
                         final Integer realCost) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.category = category;
        this.saveContent = saveContent;
        this.how = how;
        this.expectCost = expectCost;
        this.realCost = realCost;
    }
}
