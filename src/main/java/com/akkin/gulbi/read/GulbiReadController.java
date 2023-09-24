package com.akkin.gulbi.read;

import com.akkin.auth.AuthRequired;
import com.akkin.gulbi.read.dto.GulbiReadResponses;
import com.akkin.login.dto.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/api/gulbis")
@RestController
public class GulbiReadController {

    private final GulbiReadService gulbiReadService;

    @Operation(summary = "아낀 항목 리스트 보기")
    @AuthRequired
    @GetMapping
    public GulbiReadResponses getGulbis(HttpServletRequest request) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        return gulbiReadService.getGulbis(authMember.getId());
    }
}
