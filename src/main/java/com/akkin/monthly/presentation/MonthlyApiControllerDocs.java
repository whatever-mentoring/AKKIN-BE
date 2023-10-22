package com.akkin.monthly.presentation;

import com.akkin.monthly.dto.MonthlyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

@Tag(name = "Monthly Gulbi", description = "월간 아낀 항목 통계 API")
public interface MonthlyApiControllerDocs {

    @Operation(summary = " 월간 아낀 항목 조회")
    @ApiResponse(
        responseCode = "200",
        description = "월간 아낀 항목 조회 성공."
    )
    ResponseEntity<MonthlyResponse> getMonthInfo(HttpServletRequest request);
}
