package com.akkin.main.presentation;

import com.akkin.main.dto.response.MainResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

@Tag(name = "메인 정보", description = "메인 정보 API")
public interface MainApiControllerDocs {

    @Operation(summary = "메인 정보 조회", description = "메인 페이지 진입할 때 불러올 정보들")
    @Parameter(in = ParameterIn.HEADER, name = "accessToken", required = true, description = "Access Token")
    @Parameter(in = ParameterIn.HEADER, name = "refreshToken", required = true, description = "Refresh Token")
    @ApiResponse(
        responseCode = "200",
        description = "메인 정보 조회 성공."
    )
    ResponseEntity<MainResponse> enterMain(HttpServletRequest request);
}
