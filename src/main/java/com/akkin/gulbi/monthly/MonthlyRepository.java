package com.akkin.gulbi.monthly;

import com.akkin.gulbi.Gulbi;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyRepository extends JpaRepository<Gulbi, Long> {

    List<Gulbi> findByMemberIdAndSaveMonth(Long memberId, Integer saveMonth);
}
