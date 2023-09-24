package com.akkin.main;

import com.akkin.auth.AuthRequired;
import com.akkin.gulbi.Gulbi;
import com.akkin.gulbi.monthly.MonthlyService;
import com.akkin.gulbi.monthly.dto.MonthlyResponse;
import com.akkin.gulbi.weekly.WeeklyService;
import com.akkin.gulbi.weekly.dto.MemberWeeklyResponse;
import com.akkin.login.dto.AuthMember;
import com.akkin.main.dto.MainResponse;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MainController implements MainControllerDocs {

    private final MainService mainService;

    private final MonthlyService monthlyService;

    private final WeeklyService weeklyService;

    @Override
    @AuthRequired
    @GetMapping
    public MainResponse enterMain(HttpServletRequest request) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        List<Gulbi> monthGulbis = monthlyService.getMonthGulbis(authMember.getId());
        MemberWeeklyResponse weekly = weeklyService.getWeekly(authMember.getId());
        return mainService.getMainResponse(monthGulbis, weekly);
    }

}
