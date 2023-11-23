package com.akkin.gulbi;

import static com.akkin.fixture.DateFixture.자정_기준_LocalDateTime;
import static com.akkin.fixture.GulbiFixture.기타_500원_아낀_항목_만들기;
import static com.akkin.fixture.MemberFixture.회원1_만들기;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.akkin.common.UnitTest;
import com.akkin.gulbi.domain.Category;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.gulbi.dto.request.GulbiUpdateForm;
import com.akkin.member.domain.Member;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class GulbiUpdateTest extends UnitTest {

    @Test
    public void 아낀_항목_날짜_수정() {
        // given
        Member member = memberRepository.save(회원1_만들기());
        Gulbi gulbi = gulbiRepository.save(기타_500원_아낀_항목_만들기(member, 2023, 9, 9));
        GulbiUpdateForm form = new GulbiUpdateForm(2023, 9, 10, Category.ETC, "content", "how", 100, 0);

        // when
        gulbiService.update(member.getId(), gulbi.getId(), form);
        gulbi = gulbiRepository.findById(gulbi.getId()).get();

        // then
        assertThat(gulbi.getSavedAt()).isEqualTo(자정_기준_LocalDateTime(2023, 9, 10));
    }

    @Test
    public void 아낀_항목_금액_수정() {
        // given
        Member member = memberRepository.save(회원1_만들기());
        Gulbi gulbi = gulbiRepository.save(기타_500원_아낀_항목_만들기(member, 2023, 9, 9));
        // 5000 원 아끼기
        GulbiUpdateForm form = new GulbiUpdateForm(2023, 9, 10, Category.ETC, "content", "how", 6000, 1000);

        // when
        gulbiService.update(member.getId(), gulbi.getId(), form);
        gulbi = gulbiRepository.findById(gulbi.getId()).get();

        // then
        assertThat(gulbi.getSaveMoney()).isEqualTo(5000);
        assertThat(gulbi.getExpectCost()).isEqualTo(6000);
        assertThat(gulbi.getRealCost()).isEqualTo(1000);
    }

    @Test
    public void 아낀_항목_how_수정() {
        // given
        Member member = memberRepository.save(회원1_만들기());
        String newHow = "new_how";
        Gulbi gulbi = gulbiRepository.save(기타_500원_아낀_항목_만들기(member, 2023, 9, 9));
        // 5000 원 아끼기
        GulbiUpdateForm form = new GulbiUpdateForm(2023, 9, 10, Category.ETC, "content", newHow, 6000, 1000);

        // when
        gulbiService.update(member.getId(), gulbi.getId(), form);
        gulbi = gulbiRepository.findById(gulbi.getId()).get();

        // then
        assertThat(gulbi.getHow()).isEqualTo(newHow);
    }

    @Test
    public void 아낀_항목_content_수정() {
        // given
        Member member = memberRepository.save(회원1_만들기());
        String newContent = "new_content";
        Gulbi gulbi = gulbiRepository.save(기타_500원_아낀_항목_만들기(member, 2023, 9, 9));
        // 5000 원 아끼기
        GulbiUpdateForm form = new GulbiUpdateForm(2023, 9, 10, Category.ETC, newContent, "how", 6000, 1000);

        // when
        gulbiService.update(member.getId(), gulbi.getId(), form);
        gulbi = gulbiRepository.findById(gulbi.getId()).get();

        // then
        assertThat(gulbi.getSaveContent()).isEqualTo(newContent);
    }

    @Test
    public void 아낀_항목_카테고리_수정() {
        // given
        Member member = memberRepository.save(회원1_만들기());
        Category newCategory = Category.DINING;
        Gulbi gulbi = gulbiRepository.save(기타_500원_아낀_항목_만들기(member, 2023, 9, 9));
        // 5000 원 아끼기
        GulbiUpdateForm form = new GulbiUpdateForm(2023, 9, 10, newCategory, "content", "how", 6000, 1000);

        // when
        gulbiService.update(member.getId(), gulbi.getId(), form);
        gulbi = gulbiRepository.findById(gulbi.getId()).get();

        // then
        assertThat(gulbi.getCategory()).isEqualTo(newCategory);
    }
}
