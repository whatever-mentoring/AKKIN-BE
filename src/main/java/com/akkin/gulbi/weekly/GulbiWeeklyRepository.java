package com.akkin.gulbi.weekly;

import com.akkin.gulbi.Gulbi;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GulbiWeeklyRepository extends JpaRepository<Gulbi, Long> {

    @Query("SELECT g FROM Gulbi g WHERE g.member.id = :memberId AND g.saveMonth = :month AND g.saveDay BETWEEN :startDay AND :endDay")
    List<Gulbi> findByWeek(@Param("memberId") Long memberId, @Param("month") int month, @Param("startDay") int startDay, @Param("endDay") int endDay);

}
