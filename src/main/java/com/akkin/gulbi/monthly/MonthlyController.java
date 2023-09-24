package com.akkin.gulbi.monthly;

import com.akkin.auth.AuthRequired;
import com.akkin.gulbi.monthly.dto.MonthlyResponse;
import com.akkin.login.dto.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MonthlyController {

    private final MonthlyService monthlyService;

    @AuthRequired
    @GetMapping("/monthly")
    public MonthlyResponse getMonthInfo(HttpServletRequest request) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        return monthlyService.getMonthInfo(authMember.getId());
    }
}
