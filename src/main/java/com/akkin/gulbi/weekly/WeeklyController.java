package com.akkin.gulbi.weekly;

import com.akkin.auth.AuthRequired;
import com.akkin.gulbi.weekly.dto.MemberWeeklyResponse;
import com.akkin.login.dto.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class WeeklyController {

    private final WeeklyService weeklyService;

    @AuthRequired
    @GetMapping("/weekly")
    public MemberWeeklyResponse getGulbis(HttpServletRequest request) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        return weeklyService.getWeekly(authMember.getId());
    }
}
