package com.akkin.gulbi;

import static com.akkin.fixture.GulbiFixture.기타_500원_아낀_항목_만들기;
import static com.akkin.fixture.MemberFixture.회원1_만들기;
import static com.akkin.fixture.MemberFixture.회원2_만들기;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.akkin.common.UnitTest;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.gulbi.dto.response.GulbiListResponse;
import com.akkin.member.domain.Member;
import java.util.List;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class GulbiReadTest extends UnitTest {

    @Test
    public void 내_것만_가져와야_한다() {
        // given
        Member member1 = memberRepository.save(회원1_만들기());
        Member member2 = memberRepository.save(회원2_만들기());

        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member2, 2023, 9, 9));

        // when
        GulbiListResponse gulbis = gulbiService.getFirstPage(member1.getId(), 3);

        // then
        assertThat(gulbis.getEntries().size()).isEqualTo(2);
    }

    @Test
    public void 마지막_조회한_아낀_항목_ID가_맞아야한다() {
        // given
        Member member1 = memberRepository.save(회원1_만들기());
        Gulbi first = gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));

        // when
        GulbiListResponse gulbis = gulbiService.getFirstPage(member1.getId(), 3);
        List<Gulbi> all = gulbiRepository.findAll();

        // then
        assertThat(gulbis.getLastId()).isEqualTo(first.getId());
    }

    @Test
    public void 다음_정보를_가져올_수_있어야한다() {
        // given
        final int pageSize = 3;
        Member member1 = memberRepository.save(회원1_만들기());
        Gulbi lastPage = gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        Gulbi firstPage = gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        gulbiRepository.save(기타_500원_아낀_항목_만들기(member1, 2023, 9, 9));
        GulbiListResponse firstResult = gulbiService.getFirstPage(member1.getId(), pageSize);

        // when
        GulbiListResponse lastResult = gulbiService.getNextPage(member1.getId(), firstResult.getLastId(), pageSize);

        // then
        assertThat(firstResult.getLastId()).isEqualTo(firstPage.getId());
        assertThat(lastResult.getLastId()).isEqualTo(lastPage.getId());
    }
}
