package com.akkin.gulbi;

import static com.akkin.fixture.GulbiFixture.교통_500원_아낀_항목_만들기;
import static com.akkin.fixture.GulbiFixture.기타_500원_아낀_항목_만들기;
import static com.akkin.fixture.GulbiFixture.쇼핑_500원_아낀_항목_만들기;
import static com.akkin.fixture.GulbiFixture.식비_500원_아낀_항목_만들기;
import static com.akkin.fixture.MemberFixture.회원1_만들기;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.akkin.common.UnitTest;
import com.akkin.gulbi.domain.Category;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.member.domain.Member;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class GulbiCreateTest extends UnitTest {

    @Test
    public void 아낀_항목_식비_생성_테스트() {
        // given
        Member member = 회원1_만들기();
        member = memberRepository.save(member);
        Gulbi gulbi = 식비_500원_아낀_항목_만들기(member, 2023, 9, 9);

        // when
        gulbi = gulbiRepository.save(gulbi);

        // then
        assertThat(gulbi.getCategory()).isEqualTo(Category.DINING);
    }

    @Test
    public void 아낀_항목_교통_생성_테스트() {
        // given
        Member member = 회원1_만들기();
        member = memberRepository.save(member);
        Gulbi gulbi = 교통_500원_아낀_항목_만들기(member, 2023, 9, 9);

        // when
        gulbi = gulbiRepository.save(gulbi);

        // then
        assertThat(gulbi.getCategory()).isEqualTo(Category.TRAFFIC);
    }

    @Test
    public void 아낀_항목_식비_쇼핑_생성_테스트() {
        // given
        Member member = 회원1_만들기();
        member = memberRepository.save(member);
        Gulbi gulbi = 쇼핑_500원_아낀_항목_만들기(member, 2023, 9, 9);

        // when
        gulbi = gulbiRepository.save(gulbi);

        // then
        assertThat(gulbi.getCategory()).isEqualTo(Category.SHOPPING);
    }

    @Test
    public void 아낀_항목_식비_기타_생성_테스트() {
        // given
        Member member = 회원1_만들기();
        member = memberRepository.save(member);
        Gulbi gulbi = 기타_500원_아낀_항목_만들기(member, 2023, 9, 9);

        // when
        gulbi = gulbiRepository.save(gulbi);

        // then
        assertThat(gulbi.getCategory()).isEqualTo(Category.ETC);
    }
}
