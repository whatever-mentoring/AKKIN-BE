package com.akkin.gulbi.read;

import com.akkin.gulbi.read.dto.GulbiReadResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;

@Tag(name = "[DEBUG] 전체 아낀 항목", description = "주의: 아낀 항목을 많이 넣고 실행하는 응답이 매우 느림")
public interface GulbiReadControllerDocs {

    @Operation(summary = "[DEBUG] 전체 아낀 항목", parameters = {
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
            @ApiResponse(responseCode = "200", description = "사용자의 모든 아낀 항목 정보",
                content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GulbiReadResponses.class))
                })
        })
    GulbiReadResponses getGulbis(HttpServletRequest request);

}
