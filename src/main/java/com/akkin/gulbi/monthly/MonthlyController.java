package com.akkin.gulbi.monthly;

import com.akkin.auth.aop.AuthRequired;
import com.akkin.gulbi.monthly.dto.MonthlyResponse;
import com.akkin.auth.dto.AuthMember;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MonthlyController implements MonthlyControllerDocs {

    private final MonthlyService monthlyService;

    @AuthRequired
    @GetMapping("/monthly")
    public MonthlyResponse getMonthInfo(HttpServletRequest request) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        return monthlyService.getMonthInfo(authMember.getId());
    }
}
