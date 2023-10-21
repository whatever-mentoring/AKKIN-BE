package com.akkin.gulbi;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static com.akkin.fixture.GulbiFixture.굴비_만들기;
import static com.akkin.fixture.MemberFixture.회원_만들기;

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
        Member member = 회원_만들기("testName", "testEmail");
        member = memberRepository.save(member);
        Gulbi gulbi = 굴비_만들기(member, 2023, 9, 9, Category.DINING,
            "버블티 먹고 싶은데 돈 없을 때 꿀팁", "검은색 스티커를 붙이면 된다",
            500, 0);

        // when
        gulbi = gulbiRepository.save(gulbi);

        // then
        assertThat(gulbi.getCategory()).isEqualTo(Category.DINING);
    }
    
    @Test
    public void 아낀_항목_교통_생성_테스트() {
        // given
        Member member = 회원_만들기("testName", "testEmail");
        member = memberRepository.save(member);
        Gulbi gulbi = 굴비_만들기(member, 2023, 9, 9, Category.TRAFFIC,
            "운동 꿀팁", "지하철 안 타고 걸어가면 된다",
            1400, 0);

        // when
        gulbi = gulbiRepository.save(gulbi);

        // then
        assertThat(gulbi.getCategory()).isEqualTo(Category.TRAFFIC);
    }

    @Test
    public void 아낀_항목_식비_쇼핑_생성_테스트() {
        // given
        Member member = 회원_만들기("testName", "testEmail");
        member = memberRepository.save(member);
        Gulbi gulbi = 굴비_만들기(member, 2023, 9, 9, Category.SHOPPING,
            "공짜로 쇼핑함", "아이쇼핑",
            200000, 0);

        // when
        gulbi = gulbiRepository.save(gulbi);

        // then
        assertThat(gulbi.getCategory()).isEqualTo(Category.SHOPPING);
    }

    @Test
    public void 아낀_항목_식비_기타_생성_테스트() {
        // given
        Member member = 회원_만들기("testName", "testEmail");
        member = memberRepository.save(member);
        Gulbi gulbi = 굴비_만들기(member, 2023, 9, 9, Category.ETC,
            "기타 content", "기타 how",
            200000, 0);

        // when
        gulbi = gulbiRepository.save(gulbi);

        // then
        assertThat(gulbi.getCategory()).isEqualTo(Category.ETC);
    }
}
