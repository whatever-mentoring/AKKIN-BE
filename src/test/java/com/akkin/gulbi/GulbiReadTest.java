package com.akkin.gulbi;

import static com.akkin.fixture.GulbiFixture.교통_500원_아낀_항목_만들기;
import static com.akkin.fixture.GulbiFixture.기타_500원_아낀_항목_만들기;
import static com.akkin.fixture.GulbiFixture.아낀_날짜_수정_폼_만들기;
import static com.akkin.fixture.MemberFixture.회원1_만들기;
import static com.akkin.fixture.MemberFixture.회원2_만들기;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.akkin.common.UnitTest;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.gulbi.dto.request.GulbiUpdateForm;
import com.akkin.gulbi.dto.response.GulbiListResponse;
import com.akkin.member.domain.Member;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class GulbiReadTest extends UnitTest {

    private final Long FIRST_PAGE_INDEX = Long.MAX_VALUE;

    @Test
    public void 내_것만_가져와야_한다() {
        // given
        Member member1 = memberRepository.save(회원1_만들기());
        Member member2 = memberRepository.save(회원2_만들기());

        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member2, 2023, 9, 9));

        // when
        GulbiListResponse gulbis = gulbiService.getGublis(member1.getId(), "", FIRST_PAGE_INDEX, 3);

        // then
        assertThat(gulbis.getEntries().size()).isEqualTo(2);
    }

    @Test
    public void 마지막_조회한_아낀_항목_ID가_맞아야한다() {
        // given
        final int pageSize = 3;
        Member member1 = memberRepository.save(회원1_만들기());
        Gulbi first = gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));

        // when
        GulbiListResponse gulbis = gulbiService.getGublis(member1.getId(), "", Long.MAX_VALUE, pageSize);

        // then
        assertThat(gulbis.getLastId()).isEqualTo(first.getId());
    }

    @Test
    public void 다음_정보를_가져올_수_있어야한다() {
        // given
        final int pageSize = 3;
        Member member1 = memberRepository.save(회원1_만들기());
        Gulbi lastPage = gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9)); // 1
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));                  // 2
        Gulbi firstPage = gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));// 3
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));                  // 4
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));                  // 5
        GulbiListResponse firstResult = gulbiService.getGublis(member1.getId(), "", Long.MAX_VALUE, pageSize);

        // when
        GulbiListResponse lastResult = gulbiService.getGublis(member1.getId(), "", firstResult.getLastId(), pageSize);

        // then
        assertThat(firstResult.getLastId()).isEqualTo(firstPage.getId());
        assertThat(lastResult.getLastId()).isEqualTo(lastPage.getId());
    }

    @Test
    public void 특정_카테고리만_보여주기() {
        // given
        final int pageSize = 3;
        Member member1 = memberRepository.save(회원1_만들기());
        gulbiRepository.save(교통_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(교통_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(교통_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));

        // when
        GulbiListResponse traffic = gulbiService.getGublis(member1.getId(), "TRAFFIC", Long.MAX_VALUE, pageSize);

        // then
        assertThat(traffic.getEntries().size()).isEqualTo(pageSize);
    }

    @Test
    public void 특정_카테고리_페이지네이션() {
        // given
        final int pageSize = 3;
        Member member1 = memberRepository.save(회원1_만들기());
        gulbiRepository.save(교통_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        Gulbi firstPage = gulbiRepository.save(교통_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(교통_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(교통_500원_아낀_항목_만들기(member1, 2023, 9, 9));

        // when
        GulbiListResponse traffic = gulbiService.getGublis(member1.getId(), "TRAFFIC", Long.MAX_VALUE, pageSize);

        // then
        assertThat(traffic.getLastId()).isEqualTo(firstPage.getId());
    }

    @Test
    public void 없는_카테고리면_빈_응답() {
        // given
        final int pageSize = 3;
        Member member1 = memberRepository.save(회원1_만들기());
        gulbiRepository.save(교통_500원_아낀_항목_만들기(member1, 2023, 9, 9));

        // when
        GulbiListResponse traffic = gulbiService.getGublis(member1.getId(), "NONE", Long.MAX_VALUE, pageSize);

        // then
        assertThat(traffic.getEntries().isEmpty()).isTrue();
    }

    @Test
    public void 아낀_날짜_최근순으로_보여주기() {
        // given
        final int pageSize = 4;
        Member member1 = memberRepository.save(회원1_만들기());
        Gulbi gulbi1 = gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));  // 3순위
        Gulbi gulbi2 = gulbiRepository.save(교통_500원_아낀_항목_만들기(member1, 2023, 9, 8));  // 4순위
        Gulbi gulbi3 = gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 10)); // 1 순위
        Gulbi gulbi4 = gulbiRepository.save(교통_500원_아낀_항목_만들기(member1, 2023, 9, 9));  // 2순위

        // when
        GulbiListResponse results = gulbiService.getGublis(member1.getId(), "", Long.MAX_VALUE, pageSize);

        // then
        assertThat(results.getElement(0).getId()).isEqualTo(gulbi3.getId());
        assertThat(results.getElement(1).getId()).isEqualTo(gulbi4.getId());
        assertThat(results.getElement(2).getId()).isEqualTo(gulbi1.getId());
        assertThat(results.getElement(3).getId()).isEqualTo(gulbi2.getId());
    }
    
    @Test
    public void 아낀_항목_날짜_수정() {
        // given
        Member member = memberRepository.save(회원1_만들기());
        Gulbi before = gulbiRepository.save(기타_500원_아낀_항목_만들기(member, 2023, 9, 9));
        LocalDateTime beforeSavedAt = before.getSavedAt();
        GulbiUpdateForm updateForm = 아낀_날짜_수정_폼_만들기(before, 2023, 9, 10);
        gulbiService.update(member.getId(), before.getId(), updateForm);

        // when
        Gulbi after = gulbiRepository.findById(before.getId()).get();

        // then
        assertThat(after.getSavedAt()).isNotEqualTo(beforeSavedAt);
    }
}
