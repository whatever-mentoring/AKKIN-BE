package com.akkin.main.application;

import com.akkin.gulbi.dto.response.GulbiListResponse;
import com.akkin.gulbi.dto.response.GulbiResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MainService {

    public GulbiListResponse parseTodayGulbis(GulbiListResponse gulbiListResponse) {
        List<GulbiResponse> entries = gulbiListResponse.getEntries();
        List<GulbiResponse> todayEntries = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate createdAt;
        for (GulbiResponse gulbi : entries) {
            createdAt = LocalDate.of(gulbi.getYear(), gulbi.getMonth(), gulbi.getDay());
            if (!today.isEqual(createdAt)) {
                break;
            }
            todayEntries.add(gulbi);
        }
        return new GulbiListResponse(todayEntries);
    }

}
