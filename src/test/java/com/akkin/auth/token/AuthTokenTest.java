package com.akkin.auth.token;

import static com.akkin.auth.token.AuthTokenService.accessTokenMap;
import static com.akkin.fixture.MemberFixture.회원_만들기;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.akkin.auth.apple.dto.AppleUser;
import com.akkin.auth.dto.AuthMember;
import com.akkin.common.UnitTest;
import com.akkin.common.exception.UnauthorizedException;
import com.akkin.member.Member;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class AuthTokenTest extends UnitTest {

    @Test
    public void 인증토큰_발급_테스트() {
        // given
        Member member = memberRepository.save(회원_만들기("test", "test@test.com"));
        AuthToken authToken = authTokenService.issue(member);

        // when
        AuthMember authMember = accessTokenMap.get(authToken.getAccessToken());

        // then
        assertThat(authMember.getId()).isEqualTo(member.getId());
    }

    @Test
    public void 재인증시_마지막_로그인_시간_갱신() {
        // given
        Member member1 = memberRepository.save(회원_만들기("test", "test@test.com"));

        AppleUser appleUser = new AppleUser("test", "test@test.com");
        Member member2 = memberService.saveOrUpdateMember(appleUser);

        // when
        memberRepository.findById(member1.getId());

        // then
        assertThat(member1.getId()).isEqualTo(member2.getId());
        assertThat(member2.getUpdatedAt()).isAfter(member1.getCreatedAt());
    }

    @Test
    public void 재인증시_토큰재발급() {
        // given
        Member member1 = memberRepository.save(회원_만들기("test", "test@test.com"));

        AppleUser appleUser = new AppleUser("test", "test@test.com");
        Member member2 = memberService.saveOrUpdateMember(appleUser);
        AuthToken newAuthToken = authTokenService.issue(member2);

        // when
        AuthMember authMember = accessTokenMap.get(newAuthToken.getAccessToken());

        // then
        assertThat(authMember.getId()).isEqualTo(member1.getId());
        assertThat(authMember.getId()).isEqualTo(member2.getId());
    }

    @Test
    public void 재인증시_DB엔_컬럼_하나만_있어야함() {
        // given
        Member member1 = memberRepository.save(회원_만들기("test", "test@test.com"));
        AppleUser appleUser = new AppleUser("test", "test@test.com");
        authTokenService.issue(member1);

        Member member2 = memberService.saveOrUpdateMember(appleUser);
        authTokenService.issue(member2);

        // when
        List<AuthToken> authTokens = authTokenRepository.findAllByMemberId(member1.getId());

        // then
        assertThat(authTokens.size()).isEqualTo(1);
    }

    @Test
    public void 액세스토큰만_만료시_토큰재발급() {
        // given
        Member member = memberRepository.save(회원_만들기("test", "test@test.com"));
        AuthToken beforeAuthToken = authTokenService.issue(member);
        String beforeAccessToken = beforeAuthToken.getAccessToken();
        String beforeRefreshToken = beforeAuthToken.getRefreshToken();

        // when
        AuthToken newAuthToken = authTokenService.reIssueAuthToken(beforeAuthToken);

        // then
        assertThat(newAuthToken.getAccessToken()).isNotEqualTo(beforeAccessToken);
        assertThat(newAuthToken.getRefreshToken()).isNotEqualTo(beforeRefreshToken);
        assertThat(newAuthToken.getMemberId()).isEqualTo(beforeAuthToken.getMemberId());
    }

    @Test
    public void 리프레시토큰도_만료되면_예외발생() {
        // given
        Member member = memberRepository.save(회원_만들기("test", "test@test.com"));
        AuthToken authToken = authTokenService.issue(member);
        authToken.setExpiredAt(LocalDateTime.now().minusYears(1));

        // when
        boolean result = authTokenService.isRefreshTokenValid(authToken.getExpiredAt());

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void WAS_재시작으로_인증캐시가_없어진경우_토큰재발급() {
        // given
        Member member = memberRepository.save(회원_만들기("test", "test@test.com"));
        AuthToken beforeAuthToken = authTokenService.issue(member);
        // WAS 재시작해서 Map 정보가 없어졌다 가정
        accessTokenMap.remove(beforeAuthToken.getAccessToken());
        String beforeAccessToken = beforeAuthToken.getAccessToken();
        String beforeRefreshToken = beforeAuthToken.getRefreshToken();

        // when
        beforeAuthToken = authTokenService.getAuthToken(beforeAccessToken, beforeRefreshToken);
        AuthToken newAuthToken = authTokenService.reIssueAuthToken(beforeAuthToken);

        // then
        assertThat(newAuthToken.getAccessToken()).isNotEqualTo(beforeAccessToken);
        assertThat(newAuthToken.getRefreshToken()).isNotEqualTo(beforeRefreshToken);
        assertThat(newAuthToken.getMemberId()).isEqualTo(beforeAuthToken.getMemberId());
    }

    @Test
    public void 로그아웃하면_DB_토큰_삭제() {
        // given
        Member member = memberRepository.save(회원_만들기("test", "test@test.com"));
        AuthToken authToken = authTokenService.issue(member);
        authTokenService.deleteAuthToken(authToken.getAccessToken());

        // when & then
        assertThrows(UnauthorizedException.class, () ->
                authTokenService.getAuthToken(authToken.getAccessToken(), authToken.getRefreshToken())
        );
    }

    @Test
    public void 로그아웃하면_로컬캐시_토큰_삭제() {
        // given
        Member member = memberRepository.save(회원_만들기("test", "test@test.com"));
        AuthToken authToken = authTokenService.issue(member);
        authTokenService.deleteAuthToken(authToken.getAccessToken());

        // when
        AuthMember authMember = accessTokenMap.get(authToken.getAccessToken());

        // then
        assertThat(authMember).isNull();
    }
}
