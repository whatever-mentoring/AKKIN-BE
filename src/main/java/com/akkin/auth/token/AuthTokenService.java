package com.akkin.auth.token;

import com.akkin.auth.dto.AuthMember;
import com.akkin.common.exception.UnauthorizedException;
import com.akkin.member.Member;
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

    @Transactional
    public void updateAuthToken(AuthToken authToken) {
        authToken.reIssuance();
    }

    public AuthToken getAuthToken(String accessToken, String refreshToken) {
        return authTokenRepository.findByAccessTokenAndRefreshToken(accessToken, refreshToken)
            .orElseThrow(() -> new UnauthorizedException("존재하지 않은 access 및 refresh 토큰"));
    }

}
