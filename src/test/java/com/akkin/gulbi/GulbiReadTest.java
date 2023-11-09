package com.akkin.gulbi;

import static com.akkin.fixture.GulbiFixture.기타_500원_아낀_항목_만들기;
import static com.akkin.fixture.MemberFixture.회원1_만들기;
import static com.akkin.fixture.MemberFixture.회원2_만들기;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.akkin.common.UnitTest;
import com.akkin.gulbi.dto.response.GulbiListResponse;
import com.akkin.member.domain.Member;
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
        GulbiListResponse gulbis = gulbiService.getGulbis(member1.getId());

        // then
        assertThat(gulbis.getEntries().size()).isEqualTo(2);
    }
}
