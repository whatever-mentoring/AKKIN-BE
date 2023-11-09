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

    private Long id;

    private Integer year;

    private Integer month;

    private Integer day;

    private Category category;

    private String content;

    private String how;

    private Integer expectCost; // 기존 비용

    private Integer realCost;   // 실제 아낀 비용

    public GulbiResponse(final Long id,
                         final Integer year,
                         final Integer month,
                         final Integer day,
                         final Category category,
                         final String content,
                         final String how,
                         final Integer expectCost,
                         final Integer realCost) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.category = category;
        this.content = content;
        this.how = how;
        this.expectCost = expectCost;
        this.realCost = realCost;
    }
}
