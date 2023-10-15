package com.akkin.gulbi.weekly.presentation;

import com.akkin.gulbi.weekly.dto.MemberWeeklyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;

@Tag(name = "주간 아낀 항목 통계", description = "주간 아낀 항목 통계 API")
public interface WeeklyApiControllerDocs {

    @Operation(summary = "주간 아낀 항목 통계 가져오기", parameters = {
        @Parameter(
            in = ParameterIn.HEADER,
            name = "accessToken",
            required = true,
            schema = @Schema(type = "string"),
            description = "Access Token"),
        @Parameter(
            in = ParameterIn.HEADER,
            name = "refreshToken",
            required = true,
            schema = @Schema(type = "string"),
            description = "Refresh Token")
    },
        responses = {
            @ApiResponse(responseCode = "200", description = "서버 기준 해당 주의 아낀 통계",
                content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MemberWeeklyResponse.class))
                })
        })
    MemberWeeklyResponse getGulbis(HttpServletRequest request);
}
