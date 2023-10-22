package com.akkin.main.application;

import static com.akkin.common.date.DateUtil.getWeekOfMonth;

import com.akkin.common.date.MonthWeekInfo;
import com.akkin.gulbi.domain.Category;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.monthly.dto.AkkinEntry;
import com.akkin.weekly.dto.MemberWeeklyResponse;
import com.akkin.weekly.dto.WeeklyGulbiEntry;
import com.akkin.main.dto.MainResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MainService {

    public MainResponse getMainResponse(List<Gulbi> monthGulbis, MemberWeeklyResponse weekly) {
        MonthWeekInfo weekOfMonth = getWeekOfMonth(LocalDate.now());
        int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        Integer weeklyTotalCost = 0;
        EnumMap<Category, Integer> categoryExpenses = new EnumMap<>(Category.class);
        List<AkkinEntry> dailyEntries = new ArrayList<>();
        for (Gulbi gulbi : monthGulbis) {
            categoryExpenses.put(gulbi.getCategory(),
                categoryExpenses.getOrDefault(gulbi.getCategory(), 0) + gulbi.getSaveDay());
            if (gulbi.getSaveDay() == today) {
                dailyEntries.add(new AkkinEntry(gulbi));
            }
        }
        List<WeeklyGulbiEntry> weeklyGulbis = weekly.getEntries();
        for (WeeklyGulbiEntry gulbi : weeklyGulbis) {
            weeklyTotalCost += gulbi.getSaveCost();
        }
        return MainResponse.builder()
            .month(weekOfMonth.getMonth())
            .weekOfMonth(weekOfMonth.getWeek())
            .weeklyTotalCost(weeklyTotalCost)
            .monthlyDining(categoryExpenses.getOrDefault(Category.DINING, 0))
            .monthlyTraffic(categoryExpenses.getOrDefault(Category.TRAFFIC, 0))
            .monthlyShopping(categoryExpenses.getOrDefault(Category.SHOPPING, 0))
            .monthlyEtc(categoryExpenses.getOrDefault(Category.ETC, 0))
            .entries(dailyEntries)
            .build();
    }

}
