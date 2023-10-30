package com.akkin.common.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import org.springframework.stereotype.Component;

@Component
public class DateUtil {

    public static int getTodayMonth() {
        LocalDate today = LocalDate.now();
        return today.getMonthValue();
    }

    public static MonthWeekInfo getWeekOfMonth(LocalDate date) {
        final LocalDate firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());

        // 해당 날짜가 해당 월의 몇 번째 주인지 계산
        final int weekOfMonth = ((date.getDayOfMonth() + firstDayOfMonth.getDayOfWeek().getValue() % 7) / 7) + 1;

        // 그 주의 시작하는 요일과 끝나는 요일을 구함
        final LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        final LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        // 이전 주의 시작과 끝 구하기
        final LocalDate startOfPrevWeek = startOfWeek.minusWeeks(1);
        final LocalDate endOfPrevWeek = endOfWeek.minusWeeks(1);

        System.out.println("이전 주 시작: " + startOfPrevWeek);
        System.out.println("이전 주 끝: " + endOfPrevWeek);

        // 현재 일자와 비교하여 다음주 정보 출력 결정
        LocalDate now = LocalDate.now();

        // 현재 일자가 최신 주차면, 다음 주에 대한 정보를 현재 날짜로 담는다.
        if (!now.isAfter(endOfWeek)) {
            return new MonthWeekInfo(
                date.getMonthValue(),
                startOfWeek.getDayOfMonth(),
                startOfPrevWeek,
                endOfPrevWeek,
                startOfWeek,
                endOfWeek);
        }

        // 다음 주의 시작과 끝 구하기
        LocalDate startOfNextWeek = startOfWeek.plusWeeks(1);
        LocalDate endOfNextWeek = endOfWeek.plusWeeks(1);

        return new MonthWeekInfo(
            date.getMonthValue(),
            startOfWeek.getDayOfMonth(),
            startOfPrevWeek,
            endOfPrevWeek,
            startOfNextWeek,
            endOfNextWeek);
    }
}
