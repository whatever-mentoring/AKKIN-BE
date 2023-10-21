package com.akkin.gulbi.presentation;

import com.akkin.gulbi.dto.GulbiCreateForm;
import com.akkin.gulbi.dto.GulbiCreateResponse;
import com.akkin.gulbi.dto.GulbiReadResponses;
import com.akkin.gulbi.dto.GulbiUpdateForm;
import com.akkin.gulbi.dto.GulbiUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface GulbiApiControllerDocs {

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

    ResponseEntity<Void> deleteGulbi(Long gulbiId, HttpServletRequest request);

    GulbiReadResponses getGulbis(HttpServletRequest request);

    GulbiUpdateResponse updateGulbi(HttpServletRequest request, @PathVariable("id") Long gulbiId, @RequestBody GulbiUpdateForm form);


}
