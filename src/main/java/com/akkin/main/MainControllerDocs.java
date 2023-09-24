package com.akkin.main;

import com.akkin.main.dto.MainResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;

@Tag(name = "메인 정보", description = "메인 정보 API")
public interface MainControllerDocs {

    @Operation(summary = "메인 정보", parameters = {
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
            @ApiResponse(responseCode = "200", description = "메인 정보에 쓸 데이터",
                content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MainResponse.class))
                })
        })
    MainResponse enterMain(HttpServletRequest request);
}
