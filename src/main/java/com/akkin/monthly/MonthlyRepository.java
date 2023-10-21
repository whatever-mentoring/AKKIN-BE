package com.akkin.monthly;

import com.akkin.gulbi.domain.Gulbi;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyRepository extends JpaRepository<Gulbi, Long> {

    List<Gulbi> findByMemberIdAndSaveMonth(Long memberId, Integer saveMonth);
}
