package com.akkin.gulbi.persistence;

import com.akkin.gulbi.domain.Gulbi;
import com.akkin.gulbi.domain.GulbiCategory;
import com.akkin.gulbi.dto.response.GulbiResponse;
import com.akkin.member.domain.Member;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GulbiRepository extends JpaRepository<Gulbi, Long> {

    @Query("SELECT new com.akkin.gulbi.dto.response.GulbiResponse(g.id, " +
        "                                                         YEAR(g.savedAt), " +
        "                                                         MONTH(g.savedAt), " +
        "                                                         DAY(g.savedAt), " +
        "                                                         g.imageUrl, " +
        "                                                         g.category, " +
        "                                                         g.saveContent, " +
        "                                                         g.how, " +
        "                                                         g.expectCost, " +
        "                                                         g.realCost) " +
        "FROM Gulbi g LEFT JOIN g.member m " +
        "WHERE m.id = :memberId " +
        "AND g.id < :lastId " +
        "ORDER BY g.savedAt DESC, g.id DESC")
    List<GulbiResponse> findGulbiResponseByMemberId(@Param("memberId") Long memberId,
                                                    @Param("lastId") Long lastId,
                                                    Pageable pageable);

    @Query("SELECT new com.akkin.gulbi.dto.response.GulbiResponse(g.id, " +
        "                                                         YEAR(g.savedAt), " +
        "                                                         MONTH(g.savedAt), " +
        "                                                         DAY(g.savedAt), " +
        "                                                         g.imageUrl, " +
        "                                                         g.category, " +
        "                                                         g.saveContent, " +
        "                                                         g.how, " +
        "                                                         g.expectCost, " +
        "                                                         g.realCost) " +
        "FROM Gulbi g LEFT JOIN g.member m " +
        "WHERE m.id = :memberId " +
        "AND g.id < :lastId " +
        "AND g.category = :category " +
        "ORDER BY g.savedAt DESC, g.id DESC")
    List<GulbiResponse> findGulbiResponseByMemberIdAndCategory(@Param("memberId") Long memberId,
                                                               @Param("category") GulbiCategory category,
                                                               @Param("lastId") Long lastId,
                                                               Pageable pageable);


    @Query("SELECT g.id " +
           "FROM Gulbi g " +
           "WHERE g.member.id = :memberId " +
           "AND YEAR(g.savedAt) = YEAR(CURRENT_DATE) " +
           "AND MONTH(g.savedAt) = MONTH(CURRENT_DATE) " +
           "AND DAY(g.savedAt) = DAY(CURRENT_DATE) "
    )
    List<Long> countTodayGulbiCreate(@Param("memberId") Long memberId);

    void deleteAllByMember(Member member);


}
