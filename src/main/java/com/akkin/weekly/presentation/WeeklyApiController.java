package com.akkin.weekly.presentation;

import com.akkin.auth.aop.AuthRequired;
import com.akkin.auth.dto.AuthMember;
import com.akkin.weekly.application.GulbiWeeklyService;
import com.akkin.weekly.dto.MemberWeeklyResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class WeeklyApiController implements WeeklyApiControllerDocs {

    private final GulbiWeeklyService gulbiWeeklyService;

    @Override
    @AuthRequired
    @GetMapping("/weekly")
    public ResponseEntity<MemberWeeklyResponse> getWeekInfo(HttpServletRequest request) {
        final AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        final MemberWeeklyResponse response = gulbiWeeklyService.getWeekly(authMember.getId());
        return ResponseEntity.ok(response);
    }
}
