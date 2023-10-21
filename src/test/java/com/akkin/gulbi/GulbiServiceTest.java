package com.akkin.gulbi;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static com.akkin.fixture.GulbiFixture.굴비_만들기;
import static com.akkin.fixture.MemberFixture.회원_만들기;

import com.akkin.common.UnitTest;
import com.akkin.gulbi.domain.Category;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.member.Member;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class GulbiServiceTest extends UnitTest {

    @Test
    public void 식비_굴비_생성_테스트() {
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
}
