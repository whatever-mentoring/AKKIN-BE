package com.akkin.gulbi.update;

import com.akkin.auth.AuthRequired;
import com.akkin.gulbi.update.dto.GulbiUpdateForm;
import com.akkin.gulbi.update.dto.GulbiUpdateResponse;
import com.akkin.login.dto.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/api/gulbis")
@RestController
public class GulbiUpdateController {

    private final GulbiUpdateService gulbiUpdateService;

    @Operation(summary = "아낀 항목 수정", parameters = {
        @Parameter(in = ParameterIn.HEADER, name = "accessToken", required = true, schema = @Schema(type = "string")),
        @Parameter(in = ParameterIn.HEADER, name = "refreshToken", required = true, schema = @Schema(type = "string"))
    })
    @AuthRequired
    @PatchMapping("/{id}")
    public GulbiUpdateResponse updateGulbi(HttpServletRequest request,
        @PathVariable("id") Long gulbiId, @RequestBody GulbiUpdateForm form) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        GulbiUpdateResponse gulbiUpdateResponse = gulbiUpdateService.updateGulbi(authMember.getId(),
            gulbiId, form);
        return gulbiUpdateResponse;
    }
}
