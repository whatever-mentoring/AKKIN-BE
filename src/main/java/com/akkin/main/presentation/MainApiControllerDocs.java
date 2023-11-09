package com.akkin.main.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

@Tag(name = "메인 정보", description = "메인 정보 API")
public interface MainApiControllerDocs {

    @Operation(summary = "메인 정보 조회")
    @ApiResponse(
        responseCode = "200",
        description = "메인 정보 조회 성공."
    )
    ResponseEntity<Void> enterMain(HttpServletRequest request);
}
