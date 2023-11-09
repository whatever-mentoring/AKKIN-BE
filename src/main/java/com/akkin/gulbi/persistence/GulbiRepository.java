package com.akkin.gulbi.persistence;

import com.akkin.gulbi.domain.Gulbi;
import com.akkin.gulbi.dto.response.GulbiResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GulbiRepository extends JpaRepository<Gulbi, Long> {

    @Query("SELECT new com.akkin.gulbi.dto.response.GulbiResponse(g.id, YEAR(g.savedAt), MONTH(g.savedAt), DAY(g.savedAt), g.category, g.content, g.how, g.expectCost, g.realCost) " +
        "FROM Gulbi g LEFT JOIN g.member m " +
        "WHERE m.id = :memberId")
    List<GulbiResponse> findGulbiResponseByMemberId(@Param("memberId") Long memberId);
}
