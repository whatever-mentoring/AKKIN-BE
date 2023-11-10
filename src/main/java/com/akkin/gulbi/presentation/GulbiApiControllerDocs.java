package com.akkin.gulbi.presentation;

import com.akkin.gulbi.dto.request.GulbiCreateForm;
import com.akkin.gulbi.dto.response.GulbiListResponse;
import com.akkin.gulbi.dto.request.GulbiUpdateForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Tag(name = "Gulbi", description = "아낀 항목 관련 API")
public interface GulbiApiControllerDocs {

    @Operation(summary = "아낀 항목 생성")
    @ApiResponse(
        responseCode = "201",
        description = "아낀 항목 생성 성공."
    )
    ResponseEntity<Void> saveGulbi(GulbiCreateForm form, HttpServletRequest request);

    @Operation(summary = "아낀 항목 삭제")
    @ApiResponse(
        responseCode = "204",
        description = "아낀 항목 삭제 성공."
    )
    ResponseEntity<Void> deleteGulbi(Long gulbiId, HttpServletRequest request);

    @Operation(summary = "아낀 항목 조회")
    @ApiResponse(
        responseCode = "200",
        description = "아낀 항목 조회 성공."
    )
    ResponseEntity<GulbiListResponse> getGulbis(@RequestParam(value = "lastId", required = false) Optional<Long> lastId,
                                                HttpServletRequest request);

    @Operation(summary = "아낀 항목 수정")
    @ApiResponse(
        responseCode = "200",
        description = "아낀 항목 수정 성공."
    )
    ResponseEntity<Void> updateGulbi(@PathVariable("id") Long gulbiId,
        @RequestBody GulbiUpdateForm form,
        HttpServletRequest request);
}
