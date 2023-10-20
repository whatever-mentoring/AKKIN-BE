package com.akkin.auth.token;

import com.akkin.auth.dto.AuthMember;
import com.akkin.common.exception.UnauthorizedException;
import com.akkin.member.Member;
import com.akkin.member.MemberService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthTokenService {

    private final AuthTokenRepository authTokenRepository;

    private final MemberService memberService;

    public static ConcurrentHashMap<String, AuthMember> accessTokenMap = new ConcurrentHashMap(256);

    @Transactional
    public AuthToken issue(Member member) {
        final Optional<AuthToken> byMemberId = authTokenRepository.findByMemberId(member.getId());
        AuthToken authToken;
        if(byMemberId.isEmpty()) {
            authToken = authTokenRepository.save(new AuthToken(member.getId()));
        }
        else {
            authToken = byMemberId.get();
            final String accessToken = authToken.getAccessToken();
            accessTokenMap.remove(accessToken);
            authToken.reIssuance();
        }
        log.info("memberId: " + member.getId());
        log.info("access 토근 발급: " + authToken.getAccessToken());
        log.info("refresh 토근 발급: " + authToken.getRefreshToken());
        final AuthMember authMember = new AuthMember(member);
        accessTokenMap.put(authToken.getAccessToken(), authMember);
        return authToken;
    }

    public boolean isAccessTokenValid(final LocalDateTime accessTokenCreatedAt) {
        final LocalDateTime now = LocalDateTime.now();
        if (Duration.between(accessTokenCreatedAt, now).toSeconds() > 3600) {
            return false;
        }
        return true;
    }

    public boolean isRefreshTokenValid(final LocalDateTime refreshTokenExpiredAt) {
        final LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(refreshTokenExpiredAt)) {
            return false;
        }
        return true;
    }

    public AuthToken getAuthToken(final String accessToken, final String refreshToken) {
        return authTokenRepository.findByAccessTokenAndRefreshToken(accessToken, refreshToken)
            .orElseThrow(() -> new UnauthorizedException("존재하지 않은 access 및 refresh 토큰"));
    }

    @Transactional
    public AuthToken reIssueAuthToken(final AuthToken authToken) {
        // 기존 인증 캐시 삭제
        accessTokenMap.remove(authToken.getAccessToken());

        final Member member = memberService.findMember(authToken.getMemberId());
        AuthMember authMember = new AuthMember(member);

        // 새로운 인증 토큰 재발급 및 DB 갱신
        authToken.reIssuance();
        accessTokenMap.put(authToken.getAccessToken(), authMember);
        authMember.setCreatedAt(authToken.getCreatedAt());
        return authToken;
    }

    @Transactional
    public void deleteAuthToken(String accessToken) {
        if (accessToken == null) {
            return;
        }
        AuthMember authMember = accessTokenMap.get(accessToken);
        if (authMember == null) {
            return;
        }
        authTokenRepository.deleteByMemberId(authMember.getId());
        accessTokenMap.remove(accessToken);
    }
}
