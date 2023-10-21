package com.akkin.monthly.presentation;

import com.akkin.auth.aop.AuthRequired;
import com.akkin.monthly.application.MonthlyService;
import com.akkin.monthly.dto.MonthlyResponse;
import com.akkin.auth.dto.AuthMember;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MonthlyApiController implements MonthlyApiControllerDocs {

    private final MonthlyService monthlyService;

    @AuthRequired
    @GetMapping("/monthly")
    public ResponseEntity<MonthlyResponse> getMonthInfo(HttpServletRequest request) {
        final AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        final MonthlyResponse response = monthlyService.getMonthInfo(authMember.getId());
        return ResponseEntity.ok(response);
    }
}
