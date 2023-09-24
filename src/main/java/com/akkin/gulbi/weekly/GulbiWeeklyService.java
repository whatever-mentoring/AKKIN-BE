package com.akkin.gulbi.weekly;

import com.akkin.gulbi.Gulbi;
import com.akkin.gulbi.weekly.dto.WeeklyGulbiEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GulbiWeeklyService {

    private final GulbiWeeklyRepository gulbiWeeklyRepository;

    public List<WeeklyGulbiEntry> getWeekly(Long memberId, int month, int sunday) {
        List<Gulbi> byWeek = gulbiWeeklyRepository.findByWeek(memberId, month, sunday, sunday + 6);
        return byWeek.stream().map(WeeklyGulbiEntry::new).collect(Collectors.toList());
    }
}
