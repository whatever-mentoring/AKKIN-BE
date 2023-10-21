package com.akkin.gulbi.persistence;

import com.akkin.gulbi.domain.Gulbi;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GulbiRepository extends JpaRepository<Gulbi, Long> {

    List<Gulbi> findByMemberId(Long memberId);

}
