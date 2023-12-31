package com.akkin.gulbi.presentation;

import com.akkin.gulbi.dto.request.GulbiCreateForm;
import com.akkin.gulbi.dto.request.GulbiUpdateForm;
import com.akkin.gulbi.dto.response.GulbiListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Gulbi", description = "아낀 항목 관련 API")
public interface GulbiApiControllerDocs {

    @Operation(summary = "아낀 항목 생성")
    @Parameter(in = ParameterIn.HEADER, name = "accessToken", required = true, description = "Access Token")
    @Parameter(in = ParameterIn.HEADER, name = "refreshToken", required = true, description = "Refresh Token")
    @ApiResponse(
        responseCode = "201",
        description = "아낀 항목 생성 성공."
    )
    ResponseEntity<Void> saveGulbi(GulbiCreateForm form, HttpServletRequest request);

    @Operation(summary = "아낀 항목 삭제")
    @Parameter(in = ParameterIn.PATH, name = "id", required = true, description = "삭제할 아낀 항목 ID")
    @Parameter(in = ParameterIn.HEADER, name = "accessToken", required = true, description = "Access Token")
    @Parameter(in = ParameterIn.HEADER, name = "refreshToken", required = true, description = "Refresh Token")
    @ApiResponse(
        responseCode = "204",
        description = "아낀 항목 삭제 성공."
    )
    ResponseEntity<Void> deleteGulbi(Long gulbiId, HttpServletRequest request);

    @Operation(summary = "아낀 항목 조회")
    @Parameter(in = ParameterIn.PATH, name = "category", required = false,
        description = "아낀 항목의 타입. "
                    + "사용 가능한 값: DINING, TRAFFIC, SHOPPING, ETC."
                    + "빈 값으로 보내면 전체 항목을 조회하고 없는 값을 보내면 빈 리스트 반환")
    @Parameter(in = ParameterIn.PATH, name = "lastId", required = false, description = "마지막으로 탐색한 아낀 항목 ID")
    @Parameter(in = ParameterIn.HEADER, name = "accessToken", required = true, description = "Access Token")
    @Parameter(in = ParameterIn.HEADER, name = "refreshToken", required = true, description = "Refresh Token")
    @ApiResponse(
        responseCode = "200",
        description = "아낀 항목 조회 성공."
    )
    @ApiResponse(
        responseCode = "204",
        description = "아낀 항목 조회 성공. 하지만 반환할 아낀 항목이 없습니다."
    )
    ResponseEntity<GulbiListResponse> getGulbis(
        @RequestParam(value = "category", required = false) String category,
        @RequestParam(value = "lastId", required = false) Long lastId,
        HttpServletRequest request);

    @Operation(summary = "아낀 항목 수정")
    @Parameter(in = ParameterIn.PATH, name = "id", required = true, description = "수정할 아낀 항목 ID")
    @Parameter(in = ParameterIn.HEADER, name = "accessToken", required = true, description = "Access Token")
    @Parameter(in = ParameterIn.HEADER, name = "refreshToken", required = true, description = "Refresh Token")
    @ApiResponse(
        responseCode = "200",
        description = "아낀 항목 수정 성공."
    )
    ResponseEntity<Void> updateGulbi(@PathVariable("id") Long gulbiId,
        @RequestBody GulbiUpdateForm form,
        HttpServletRequest request);
}
