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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthTokenService {

    private final AuthTokenRepository authTokenRepository;

    private final MemberService memberService;

    public static ConcurrentHashMap<String, AuthMember> accessTokenMap = new ConcurrentHashMap(256);

    @Transactional
    public AuthToken issue(Member member) {
        Optional<AuthToken> byMemberId = authTokenRepository.findByMemberId(member.getId());
        AuthToken authToken;
        if(byMemberId.isEmpty()) {
            authToken = authTokenRepository.save(new AuthToken(member.getId()));
        }
        else {
            authToken = byMemberId.get();
            String accessToken = authToken.getAccessToken();
            accessTokenMap.remove(accessToken);
            authToken.reIssuance();
        }
        AuthMember authMember = new AuthMember(member);
        accessTokenMap.put(authToken.getAccessToken(), authMember);
        return authToken;
    }

    public boolean isAccessTokenValid(LocalDateTime accessTokenCreatedAt) {
        LocalDateTime now = LocalDateTime.now();
        if (Duration.between(accessTokenCreatedAt, now).toSeconds() > 3600) {
            return false;
        }
        return true;
    }

    public boolean isRefreshTokenValid(LocalDateTime refreshTokenExpiredAt) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(refreshTokenExpiredAt)) {
            return false;
        }
        return true;
    }

    public AuthToken getAuthToken(String accessToken, String refreshToken) {
        return authTokenRepository.findByAccessTokenAndRefreshToken(accessToken, refreshToken)
            .orElseThrow(() -> new UnauthorizedException("존재하지 않은 access 및 refresh 토큰"));
    }

    @Transactional
    public AuthToken reIssueAuthToken(AuthToken authToken) {
        // 기존 인증 캐시 삭제
        accessTokenMap.remove(authToken.getAccessToken());

        Member member = memberService.findMember(authToken.getMemberId());
        AuthMember authMember = new AuthMember(member);

        // 새로운 인증 토큰 재발급 및 DB 갱신
        authToken.reIssuance();
        accessTokenMap.put(authToken.getAccessToken(), authMember);
        authMember.setCreatedAt(authToken.getCreatedAt());
        return authToken;
    }
}
