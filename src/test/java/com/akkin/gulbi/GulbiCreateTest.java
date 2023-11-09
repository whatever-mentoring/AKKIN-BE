package com.akkin.gulbi;

import static com.akkin.fixture.GulbiFixture.아낀_항목_생성_폼_만들기;
import static com.akkin.fixture.MemberFixture.회원1_만들기;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.akkin.common.UnitTest;
import com.akkin.gulbi.domain.Category;
import com.akkin.gulbi.dto.request.GulbiCreateForm;
import com.akkin.gulbi.dto.response.GulbiListResponse;
import com.akkin.gulbi.dto.response.GulbiResponse;
import com.akkin.member.domain.Member;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class GulbiCreateTest extends UnitTest {

    @Test
    public void 아낀_항목_식비_생성_테스트() {
        // given
        Member member = memberRepository.save(회원1_만들기());
        GulbiCreateForm form = 아낀_항목_생성_폼_만들기(Category.DINING);
        gulbiService.create(member.getId(), form);

        // when
        GulbiListResponse gulbis = gulbiService.getGulbis(member.getId());
        GulbiResponse gulbiResponse = gulbis.getEntries().get(0);

        // then
        assertThat(gulbiResponse.getCategory()).isEqualTo(Category.DINING);
    }

    @Test
    public void 아낀_항목_교통_생성_테스트() {
        // given
        Member member = memberRepository.save(회원1_만들기());
        GulbiCreateForm form = 아낀_항목_생성_폼_만들기(Category.TRAFFIC);
        gulbiService.create(member.getId(), form);

        // when
        GulbiListResponse gulbis = gulbiService.getGulbis(member.getId());
        GulbiResponse gulbiResponse = gulbis.getEntries().get(0);

        // then
        assertThat(gulbiResponse.getCategory()).isEqualTo(Category.TRAFFIC);
    }

    @Test
    public void 아낀_항목_식비_쇼핑_생성_테스트() {
        // given
        Member member = memberRepository.save(회원1_만들기());
        GulbiCreateForm form = 아낀_항목_생성_폼_만들기(Category.SHOPPING);
        gulbiService.create(member.getId(), form);

        // when
        GulbiListResponse gulbis = gulbiService.getGulbis(member.getId());
        GulbiResponse gulbiResponse = gulbis.getEntries().get(0);

        // then
        assertThat(gulbiResponse.getCategory()).isEqualTo(Category.SHOPPING);
    }

    @Test
    public void 아낀_항목_식비_기타_생성_테스트() {
        // given
        Member member = memberRepository.save(회원1_만들기());
        GulbiCreateForm form = 아낀_항목_생성_폼_만들기(Category.ETC);
        gulbiService.create(member.getId(), form);

        // when
        GulbiListResponse gulbis = gulbiService.getGulbis(member.getId());
        GulbiResponse gulbiResponse = gulbis.getEntries().get(0);

        // then
        assertThat(gulbiResponse.getCategory()).isEqualTo(Category.ETC);
    }
}
