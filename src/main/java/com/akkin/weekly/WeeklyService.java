package com.akkin.weekly;

import com.akkin.weekly.dto.MemberWeeklyResponse;
import com.akkin.weekly.dto.MonthWeekInfo;
import com.akkin.weekly.dto.WeeklyGulbiEntries;
import com.akkin.weekly.dto.WeeklyGulbiEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class WeeklyService {

    private final GulbiWeeklyService gulbiWeeklyService;

    public MemberWeeklyResponse getWeekly(Long memberID) {
        MonthWeekInfo monthWeekInfo = getWeekOfMonth();
        List<WeeklyGulbiEntry> weekly = gulbiWeeklyService.getWeekly(memberID, monthWeekInfo.getMonth(), monthWeekInfo.getSunday());
        WeeklyGulbiEntries entries = new WeeklyGulbiEntries(weekly);
        return MemberWeeklyResponse.builder()
                .month(monthWeekInfo.getMonth())
                .weekOfMonth((monthWeekInfo.getWeek()))
                .entries(entries)
                .build();
    }

    private MonthWeekInfo getWeekOfMonth() {
        Calendar calendar = Calendar.getInstance();

        // Calender 에서 1월은 0
        int month = calendar.get(Calendar.MONTH) + 1;
        // 몇째 주인지
        int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
        // 그 주의 일요일 날짜
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int daysUntilSunday = Calendar.SUNDAY - dayOfWeek;
        calendar.add(Calendar.DAY_OF_MONTH, daysUntilSunday);
        int sunday = calendar.get(Calendar.DAY_OF_MONTH);

        return new MonthWeekInfo(month, weekOfMonth, sunday);
    }
}
