package com.akkin.gulbi.presentation;

import com.akkin.gulbi.dto.request.GulbiCreateForm;
import com.akkin.gulbi.dto.response.GulbiResponses;
import com.akkin.gulbi.dto.request.GulbiUpdateForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Gulbi", description = "아낀항목 관련 API 입니다.")
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
    ResponseEntity<GulbiResponses> getGulbis(HttpServletRequest request);

    @Operation(summary = "아낀 항목 수정")
    @ApiResponse(
        responseCode = "200",
        description = "아낀 항목 수정 성공."
    )
    ResponseEntity<Void> updateGulbi(@PathVariable("id") Long gulbiId,
        @RequestBody GulbiUpdateForm form,
        HttpServletRequest request);
}
