package com.akkin.gulbi.delete;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

@Tag(name = "아낀 항목 삭제", description = "아낀 항목 삭제 API")
public interface GulbiDeleteControllerDocs {

    @Operation(summary = "아낀 항목 삭제", parameters = {
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
            description = "Refresh Token"),
        @Parameter(
            in = ParameterIn.PATH,
            name = "id",
            required = true,
            schema = @Schema(type = "long"),
            description = "아낀 항목 식별자")
    },
        responses = {@ApiResponse(responseCode = "200", description = "성공적으로 아낀 항목을 삭제하였습니다.")})
    ResponseEntity<Void> deleteGulbi(Long gulbiId, HttpServletRequest request);
}
