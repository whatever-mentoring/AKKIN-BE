package com.akkin.gulbi.monthly;

import com.akkin.gulbi.Gulbi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonthlyRepository extends JpaRepository<Gulbi, Long> {

    List<Gulbi> findByMemberIdAndSaveMonth(Long memberId, Integer saveMonth);
}
