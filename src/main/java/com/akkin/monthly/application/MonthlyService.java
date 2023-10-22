package com.akkin.monthly.application;

import static com.akkin.common.date.DateUtil.getTodayMonth;

import com.akkin.gulbi.domain.Category;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.monthly.dto.MonthRanking;
import com.akkin.monthly.dto.MonthlyResponse;
import com.akkin.monthly.persistence.MonthlyRepository;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MonthlyService {

    private final MonthlyRepository monthlyRepository;

    public MonthlyResponse getMonthInfo(Long memberId) {
        List<Gulbi> gulbis = getMonthGulbis(memberId);
        return calculateMonthlyExpenses(gulbis);
    }

    public List<Gulbi> getMonthGulbis(Long memberId) {
        int month = getTodayMonth();
        return monthlyRepository.findByMemberIdAndSaveMonth(memberId, month);
    }

    public MonthlyResponse calculateMonthlyExpenses(List<Gulbi> gulbis) {
        EnumMap<Category, Integer> categoryExpenses = new EnumMap<>(Category.class);
        for (Gulbi gulbi : gulbis) {
            categoryExpenses.put(gulbi.getCategory(),
                categoryExpenses.getOrDefault(gulbi.getCategory(), 0) + gulbi.getSaveDay());
        }
        MonthRanking monthRanking = calculateTopSavings(gulbis);
        return MonthlyResponse.builder()
            .dining(categoryExpenses.getOrDefault(Category.DINING, 0))
            .traffic(categoryExpenses.getOrDefault(Category.TRAFFIC, 0))
            .shopping(categoryExpenses.getOrDefault(Category.SHOPPING, 0))
            .etc(categoryExpenses.getOrDefault(Category.ETC, 0))
            .monthRanking(monthRanking)
            .build();
    }

    private MonthRanking calculateTopSavings(List<Gulbi> gulbis) {
        // Map<SaveDay, SaveMoney>
        Map<Integer, Integer> savingsByDay = new HashMap<>();

        for (Gulbi gulbi : gulbis) {
            savingsByDay.put(gulbi.getSaveDay(),
                savingsByDay.getOrDefault(gulbi.getSaveDay(), 0) + gulbi.getSaveMoney());
        }

        // 총합을 기준으로 내림차순 정렬하는 TreeMap 생성
        TreeMap<Integer, Integer> sortedSavings = new TreeMap<>(
            (a, b) -> savingsByDay.get(b).compareTo(savingsByDay.get(a)));
        sortedSavings.putAll(savingsByDay);

        int firstDay = 0, secondDay = 0, thirdDay = 0;
        int firstAmount = 0, secondAmount = 0, thirdAmount = 0;
        int index = 0;

        for (Map.Entry<Integer, Integer> entry : sortedSavings.entrySet()) {
            if (index == 0) {
                firstDay = entry.getKey();
                firstAmount = entry.getValue();
            } else if (index == 1) {
                secondDay = entry.getKey();
                secondAmount = entry.getValue();
            } else if (index == 2) {
                thirdDay = entry.getKey();
                thirdAmount = entry.getValue();
                break;
            }
            index++;
        }

        return MonthRanking.builder()
            .firstDay(firstDay)
            .firstAmount(firstAmount)
            .secondDay(secondDay)
            .secondAmount(secondAmount)
            .thirdDay(thirdDay)
            .thirdAmount(thirdAmount)
            .build();
    }
}
