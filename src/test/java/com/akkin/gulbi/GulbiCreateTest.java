package com.akkin.gulbi;

import com.akkin.common.UnitTest;
import com.akkin.gulbi.domain.GulbiCategory;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.gulbi.dto.request.GulbiCreateForm;
import org.junit.jupiter.api.Test;

import static com.akkin.fixture.GulbiFixture.아낀_항목_생성_폼_만들기;
import static com.akkin.fixture.MemberFixture.회원1_만들기;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("NonAsciiCharacters")
public class GulbiCreateTest extends UnitTest {

    @Test
    public void 아낀_항목_식비_생성_테스트() {
        // given
        GulbiCreateForm form = 아낀_항목_생성_폼_만들기(GulbiCategory.DINING);
        Gulbi gulbi = gulbiRepository.save(form.dtoToEntity(회원1_만들기()));

        // when
        gulbi = gulbiService.getGulbiOrElseThrow(gulbi.getId());

        // then
        assertThat(gulbi.getCategory()).isEqualTo(GulbiCategory.DINING);
    }

    @Test
    public void 아낀_항목_교통_생성_테스트() {
        // given
        GulbiCreateForm form = 아낀_항목_생성_폼_만들기(GulbiCategory.TRAFFIC);
        Gulbi gulbi = gulbiRepository.save(form.dtoToEntity(회원1_만들기()));

        // when
        gulbi = gulbiService.getGulbiOrElseThrow(gulbi.getId());

        // then
        assertThat(gulbi.getCategory()).isEqualTo(GulbiCategory.TRAFFIC);
    }

    @Test
    public void 아낀_항목_식비_쇼핑_생성_테스트() {
        // given
        GulbiCreateForm form = 아낀_항목_생성_폼_만들기(GulbiCategory.SHOPPING);
        Gulbi gulbi = gulbiRepository.save(form.dtoToEntity(회원1_만들기()));

        // when
        gulbi = gulbiService.getGulbiOrElseThrow(gulbi.getId());

        // then
        assertThat(gulbi.getCategory()).isEqualTo(GulbiCategory.SHOPPING);
    }

    @Test
    public void 아낀_항목_식비_기타_생성_테스트() {
        // given
        GulbiCreateForm form = 아낀_항목_생성_폼_만들기(GulbiCategory.ETC);
        Gulbi gulbi = gulbiRepository.save(form.dtoToEntity(회원1_만들기()));

        // when
        gulbi = gulbiService.getGulbiOrElseThrow(gulbi.getId());

        // then
        assertThat(gulbi.getCategory()).isEqualTo(GulbiCategory.ETC);
    }
}
