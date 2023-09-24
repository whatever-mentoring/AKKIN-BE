package com.akkin.common.date;

import java.time.LocalDate;
import java.util.Calendar;
import org.springframework.stereotype.Component;

@Component
public class DateUtils {

    public static int getTodayMonth() {
        LocalDate today = LocalDate.now();
        return today.getMonthValue();
    }

    public static MonthWeekInfo getWeekOfMonth() {
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
