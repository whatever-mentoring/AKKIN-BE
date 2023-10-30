package com.akkin.common;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import com.akkin.common.date.MonthWeekInfo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("NonAsciiCharacters")
public class DateUtilTest extends UnitTest{

    @Test
    public void 입력된_날짜의_이전과_다음_주_정보를_보여준다() {
        // given
        LocalDate day = LocalDate.of(2023, 10, 1);

        // when
        MonthWeekInfo weekOfMonth = dateUtil.getWeekOfMonth(day);

        // then
        assertThat(weekOfMonth.getStartOfPrevWeek()).isEqualTo(LocalDate.of(2023, 9, 24));
        assertThat(weekOfMonth.getEndOfPrevWeek()).isEqualTo(LocalDate.of(2023, 9, 30));
        assertThat(weekOfMonth.getStartOfNextWeek()).isEqualTo(LocalDate.of(2023, 10, 8));
        assertThat(weekOfMonth.getEndOfNextWeek()).isEqualTo(LocalDate.of(2023, 10, 14));
    }

    @Test
    public void 최신_주면_현재_주_정보를_보여줌() {
        // given
        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        // when
        MonthWeekInfo weekOfMonth = dateUtil.getWeekOfMonth(now);

        // then
        assertThat(weekOfMonth.getStartOfNextWeek()).isEqualTo(startOfWeek);
        assertThat(weekOfMonth.getEndOfNextWeek()).isEqualTo(endOfWeek);
    }

    @Test
    public void 다음날이_달이_바뀌는_경우() {
        // given
        LocalDate day = LocalDate.of(2023, 8, 31);

        // when
        MonthWeekInfo weekOfMonth = dateUtil.getWeekOfMonth(day);

        // then
        assertThat(weekOfMonth.getStartOfPrevWeek()).isEqualTo(LocalDate.of(2023, 8, 20));
        assertThat(weekOfMonth.getEndOfPrevWeek()).isEqualTo(LocalDate.of(2023, 8, 26));
        assertThat(weekOfMonth.getStartOfNextWeek()).isEqualTo(LocalDate.of(2023, 9, 3));
        assertThat(weekOfMonth.getEndOfNextWeek()).isEqualTo(LocalDate.of(2023, 9, 9));
    }

    @Test
    public void 오늘_달이_바뀐_경우() {
        // given
        LocalDate day = LocalDate.of(2023, 9, 1);

        // when
        MonthWeekInfo weekOfMonth = dateUtil.getWeekOfMonth(day);

        // then
        assertThat(weekOfMonth.getStartOfPrevWeek()).isEqualTo(LocalDate.of(2023, 8, 20));
        assertThat(weekOfMonth.getEndOfPrevWeek()).isEqualTo(LocalDate.of(2023, 8, 26));
        assertThat(weekOfMonth.getStartOfNextWeek()).isEqualTo(LocalDate.of(2023, 9, 3));
        assertThat(weekOfMonth.getEndOfNextWeek()).isEqualTo(LocalDate.of(2023, 9, 9));
    }
}
