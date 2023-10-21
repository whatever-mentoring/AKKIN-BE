package com.akkin.monthly.persistence;

import com.akkin.gulbi.domain.Gulbi;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyRepository extends JpaRepository<Gulbi, Long> {

    List<Gulbi> findByMemberIdAndSaveMonth(final Long memberId, final Integer saveMonth);
}
