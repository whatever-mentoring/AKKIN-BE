package com.akkin.weekly;

import com.akkin.gulbi.domain.Gulbi;
import com.akkin.weekly.dto.WeeklyGulbiEntry;
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

    public List<WeeklyGulbiEntry> getWeekly(Long memberId, int month, int sunday) {
        List<Gulbi> byWeek = gulbiWeeklyRepository.findByWeek(memberId, month, sunday, sunday + 6);
        return byWeek.stream().map(WeeklyGulbiEntry::new).collect(Collectors.toList());
    }
}
