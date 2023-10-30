package com.akkin.common.date;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonthWeekInfo {

    final int month;
    final int sunday;
    final LocalDate startOfPrevWeek;
    final LocalDate endOfPrevWeek;
    final LocalDate startOfNextWeek;
    final LocalDate endOfNextWeek;

    public MonthWeekInfo(final int month, final int sunday) {
        this.month = month;
        this.sunday = sunday;
        this.startOfPrevWeek = null;
        this.endOfPrevWeek = null;
        this.startOfNextWeek = null;
        this.endOfNextWeek = null;
    }

    public MonthWeekInfo(final int month,
                        final int sunday,
                        final LocalDate startOfPrevWeek,
                        final LocalDate endOfPrevWeek,
                        final LocalDate startOfNextWeek,
                        final LocalDate endOfNextWeek) {
        this.month = month;
        this.sunday = sunday;
        this.startOfPrevWeek = startOfPrevWeek;
        this.endOfPrevWeek = endOfPrevWeek;
        this.startOfNextWeek = startOfNextWeek;
        this.endOfNextWeek = endOfNextWeek;
    }
}
