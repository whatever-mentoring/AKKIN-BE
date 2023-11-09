package com.akkin.gulbi;

import static com.akkin.fixture.GulbiFixture.식비_500원_아낀_항목_만들기;
import static com.akkin.fixture.MemberFixture.회원1_만들기;
import static com.akkin.fixture.MemberFixture.회원2_만들기;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.akkin.common.UnitTest;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.gulbi.exception.GulbiNotOwnerException;
import com.akkin.member.domain.Member;
import java.util.Optional;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class GulbiDeleteTest extends UnitTest {

    @Test
    public void 아낀_항목_삭제_테스트() {
        // given
        Member member = memberRepository.save(회원1_만들기());
        Gulbi gulbi = gulbiRepository.save(식비_500원_아낀_항목_만들기(member, 2023, 9, 9));

        // when
        gulbiService.delete(member.getId(), gulbi.getId());
        Optional<Gulbi> result = gulbiRepository.findById(gulbi.getId());

        // then
        assertThat(result.isEmpty()).isEqualTo(true);
    }

    @Test
    public void 내가_쓴_것만_삭제_가능() {
        // given
        Member member1 = memberRepository.save(회원1_만들기());
        Member member2 = memberRepository.save(회원2_만들기());
        Gulbi gulbi = gulbiRepository.save(식비_500원_아낀_항목_만들기(member1, 2023, 9, 9));

        // when & then
        assertThrows(GulbiNotOwnerException.class, () -> {
            gulbiService.delete(member2.getId(), gulbi.getId());
        });
    }
}
