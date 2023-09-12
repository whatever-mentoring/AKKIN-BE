package com.akkin.gulbi;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GulbiRepository extends JpaRepository<Gulbi, Long> {

    List<Gulbi> findByMemberId(Long memberId);

}
