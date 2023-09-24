package com.akkin.gulbi.create;

import com.akkin.gulbi.create.dto.GulbiCreateForm;
import com.akkin.gulbi.create.dto.GulbiCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "아낀 항목 생성", description = "아낀 항목 생성 API")
public interface GulbiCreateControllerDocs {

    @Operation(summary = "아낀 항목 생성", parameters = {
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
        requestBody = @RequestBody(description = "사용자가 등록하려는 아낀 정보", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = GulbiCreateForm.class))}),
        responses = {
            @ApiResponse(responseCode = "200", description = "서버에 저장된 아낀 정보",
                content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GulbiCreateResponse.class))
                })
        })
    GulbiCreateResponse saveGulbi(GulbiCreateForm form, HttpServletRequest request);
}
