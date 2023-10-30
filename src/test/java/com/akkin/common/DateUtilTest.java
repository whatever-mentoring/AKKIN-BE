package com.akkin.common;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.akkin.common.date.WeekInfo;;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class DateUtilTest extends UnitTest {

    @Test
    public void 입력된_날짜의_이전_주_보기() {
        // given
        WeekInfo weekInfo = new WeekInfo(2023, 10, 1);

        // when
        LocalDateTime startOfPrevWeek = weekInfo.getStartOfPrevWeek();
        LocalDateTime endOfPrevWeek = weekInfo.getEndOfPrevWeek();

        // then
        assertThat(startOfPrevWeek).isEqualTo(LocalDateTime.of(2023, 9, 24, 0, 0, 0));
        assertThat(endOfPrevWeek).isEqualTo(LocalDateTime.of(2023, 9, 30, 23, 59, 59));
    }

    @Test
    public void 입력된_날짜의_시작과_끝_보기() {
        // given
        WeekInfo weekInfo = new WeekInfo(2023, 10, 1);

        // when
        LocalDateTime startOfCurrentWeek = weekInfo.getStartOfCurrentWeek();
        LocalDateTime endOfCurrentWeek = weekInfo.getEndOfCurrentWeek();

        // then
        assertThat(startOfCurrentWeek).isEqualTo(LocalDateTime.of(2023, 10, 1, 0, 0, 0));
        assertThat(endOfCurrentWeek).isEqualTo(LocalDateTime.of(2023, 10, 7, 23, 59, 59));
    }

    @Test
    public void 입력된_날짜의_다음_주_보기() {
        // given
        WeekInfo weekInfo = new WeekInfo(2023, 10, 1);

        // when
        LocalDateTime startOfNextWeek = weekInfo.getStartOfNextWeek();
        LocalDateTime endOfNextWeek = weekInfo.getEndOfNextWeek();

        // then
        assertThat(startOfNextWeek).isEqualTo(LocalDateTime.of(2023, 10, 8, 0, 0, 0));
        assertThat(endOfNextWeek).isEqualTo(LocalDateTime.of(2023, 10, 14, 23, 59, 59));
    }

    @Test
    public void 이번주가_달이_바뀌는_경우() {
        // given
        WeekInfo weekInfo = new WeekInfo(2023, 8, 31);

        // when
        LocalDateTime startOfPrevWeek = weekInfo.getStartOfPrevWeek();
        LocalDateTime endOfPrevWeek = weekInfo.getEndOfPrevWeek();
        LocalDateTime startOfCurrentWeek = weekInfo.getStartOfCurrentWeek();
        LocalDateTime endOfCurrentWeek = weekInfo.getEndOfCurrentWeek();
        LocalDateTime startOfNextWeek = weekInfo.getStartOfNextWeek();
        LocalDateTime endOfNextWeek = weekInfo.getEndOfNextWeek();

        // then
        assertThat(startOfPrevWeek).isEqualTo(LocalDateTime.of(2023, 8, 20, 0, 0, 0));
        assertThat(endOfPrevWeek).isEqualTo(LocalDateTime.of(2023, 8, 26, 23, 59, 59));
        assertThat(startOfCurrentWeek).isEqualTo(LocalDateTime.of(2023, 8, 27, 0, 0, 0));
        assertThat(endOfCurrentWeek).isEqualTo(LocalDateTime.of(2023, 9, 2, 23, 59, 59));
        assertThat(startOfNextWeek).isEqualTo(LocalDateTime.of(2023, 9, 3, 0, 0, 0));
        assertThat(endOfNextWeek).isEqualTo(LocalDateTime.of(2023, 9, 9, 23, 59, 59));
    }
}
