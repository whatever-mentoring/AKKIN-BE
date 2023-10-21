package com.akkin.weekly.presentation;

import com.akkin.weekly.dto.MemberWeeklyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

@Tag(name = "Weekly Gulbi", description = "주간 아낀 항목 통계 API")
public interface WeeklyApiControllerDocs {

    @Operation(summary = "주간 아낀 항목 조회")
    @ApiResponse(
        responseCode = "200",
        description = "주간 아낀 항목 조회 성공."
    )
    ResponseEntity<MemberWeeklyResponse> getWeekInfo(HttpServletRequest request);
}
