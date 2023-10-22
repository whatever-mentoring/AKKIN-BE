package com.akkin.main.presentation;

import com.akkin.auth.aop.AuthRequired;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.monthly.application.MonthlyService;
import com.akkin.weekly.application.GulbiWeeklyService;
import com.akkin.weekly.dto.MemberWeeklyResponse;
import com.akkin.auth.dto.AuthMember;
import com.akkin.main.application.MainService;
import com.akkin.main.dto.MainResponse;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MainApiController implements MainApiControllerDocs {

    private final MainService mainService;

    private final MonthlyService monthlyService;

    private final GulbiWeeklyService weeklyService;

    @Override
    @AuthRequired
    @GetMapping
    public ResponseEntity<MainResponse> enterMain(HttpServletRequest request) {
        final AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        final List<Gulbi> monthGulbis = monthlyService.getMonthGulbis(authMember.getId());
        final MemberWeeklyResponse weekly = weeklyService.getWeekly(authMember.getId());
        final MainResponse response = mainService.getMainResponse(monthGulbis, weekly);
        return ResponseEntity.ok(response);
    }
}
