package com.akkin.fixture;

import java.time.LocalDateTime;

@SuppressWarnings("NonAsciiCharacters")
public class DateFixture {

    public static LocalDateTime 자정_기준_LocalDateTime(int year, int month, int day) {
        return LocalDateTime.of(year, month, day, 0, 0, 0);
    }

}
