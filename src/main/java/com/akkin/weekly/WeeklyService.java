package com.akkin.weekly;

import static com.akkin.common.date.DateUtils.getWeekOfMonth;

import com.akkin.weekly.dto.MemberWeeklyResponse;
import com.akkin.common.date.MonthWeekInfo;
import com.akkin.weekly.dto.WeeklyGulbiEntry;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class WeeklyService {

    private final GulbiWeeklyService gulbiWeeklyService;

    public MemberWeeklyResponse getWeekly(Long memberID) {
        MonthWeekInfo monthWeekInfo = getWeekOfMonth();
        List<WeeklyGulbiEntry> weekly = gulbiWeeklyService.getWeekly(memberID,
            monthWeekInfo.getMonth(), monthWeekInfo.getSunday());
        return MemberWeeklyResponse.builder()
            .month(monthWeekInfo.getMonth())
            .weekOfMonth((monthWeekInfo.getWeek()))
            .weeklyGulbiEntries(weekly)
            .build();
    }
}
