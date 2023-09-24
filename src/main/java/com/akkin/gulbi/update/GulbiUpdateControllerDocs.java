package com.akkin.gulbi.update;

import com.akkin.gulbi.update.dto.GulbiUpdateForm;
import com.akkin.gulbi.update.dto.GulbiUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "아낀 항목 수정", description = "아낀 항목 수정 API")
public interface GulbiUpdateControllerDocs {

    @Operation(summary = "아낀 항목 수정", parameters = {
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
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "사용자가 수정하려는 아낀 정보", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = GulbiUpdateForm.class))}),
        responses = {
            @ApiResponse(responseCode = "200", description = "서버에 반영된 아낀 정보",
                content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GulbiUpdateResponse.class))
                })
        })
    GulbiUpdateResponse updateGulbi(HttpServletRequest request, @PathVariable("id") Long gulbiId,
        @RequestBody GulbiUpdateForm form);
}
