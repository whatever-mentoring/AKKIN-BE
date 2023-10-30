package com.akkin.common.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import lombok.Getter;

@Getter
public class WeekInfo {

    final LocalDateTime startOfPrevWeek;
    final LocalDateTime endOfPrevWeek;
    final LocalDateTime startOfCurrentWeek;
    final LocalDateTime endOfCurrentWeek;
    final LocalDateTime startOfNextWeek;
    final LocalDateTime endOfNextWeek;

    public WeekInfo(final int year, final int month, final int day) {
        final LocalDate now = LocalDate.of(year, month, day);

        final LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        final LocalDate endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        
        // 현재 주의 시작과 끝
        this.startOfCurrentWeek = LocalDateTime.of(startOfWeek, LocalTime.of(0, 0, 0));
        this.endOfCurrentWeek = LocalDateTime.of(endOfWeek, LocalTime.of(23, 59, 59));

        // 이전 주의 시작과 끝
        this.startOfPrevWeek = this.startOfCurrentWeek.minusWeeks(1);
        this.endOfPrevWeek = this.endOfCurrentWeek.minusWeeks(1);

        // 다음 주의 시작과 끝
        this.startOfNextWeek = this.startOfCurrentWeek.plusWeeks(1);
        this.endOfNextWeek = this.endOfCurrentWeek.plusWeeks(1);
    }
}
