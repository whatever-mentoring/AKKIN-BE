package com.akkin.weekly.application;

import static com.akkin.common.date.DateUtil.getWeekOfMonth;

import com.akkin.common.date.MonthWeekInfo;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.weekly.dto.MemberWeeklyResponse;
import com.akkin.weekly.persistence.GulbiWeeklyRepository;
import com.akkin.weekly.dto.WeeklyGulbiEntry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GulbiWeeklyService {

    private final GulbiWeeklyRepository gulbiWeeklyRepository;

    public MemberWeeklyResponse calc(final Long memberID) {
        final MonthWeekInfo monthWeekInfo = getWeekOfMonth(LocalDate.now());
        final List<WeeklyGulbiEntry> weekly = getWeekly(memberID,
            monthWeekInfo.getMonth(), monthWeekInfo.getSunday());
        return MemberWeeklyResponse.builder()
            .month(monthWeekInfo.getMonth())
            .weeklyGulbiEntries(weekly)
            .build();
    }

    public List<WeeklyGulbiEntry> getWeekly(final Long memberId, final int month, final int sunday) {
        final List<Gulbi> byWeek = gulbiWeeklyRepository.findByWeek(memberId, month, sunday, sunday + 6);
        return byWeek.stream().map(WeeklyGulbiEntry::new).collect(Collectors.toList());
    }
}
