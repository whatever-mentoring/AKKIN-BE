package com.akkin.auth.whitelist;

import com.akkin.auth.dto.AuthMember;
import com.akkin.auth.dto.response.AuthToken;
import com.akkin.common.exception.ExpireRefreshTokenException;
import com.akkin.common.exception.UnauthorizedException;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class WhiteTokenService {

    private final WhiteTokenRepository whiteTokenRepository;

    public static ConcurrentHashMap<String, AuthMember> accessTokenMap = new ConcurrentHashMap(256);

    @Transactional
    public WhiteToken addWhiteToken(Long memberId, AuthToken authToken) {
        return whiteTokenRepository.save(
            WhiteToken.builder()
                .memberId(memberId)
                .accessToken(authToken.getAccessToken())
                .refreshToken(authToken.getRefreshToken())
                .build()
        );
    }

    public WhiteToken getWhiteToken(Long memberId, String refreshToken) {
        return whiteTokenRepository.findByMemberIdAndRefreshToken(memberId, refreshToken)
            .orElseThrow(() -> new ExpireRefreshTokenException(
                ExpireRefreshTokenException.NON_EXISTENT_TOKEN_MSG));
    }

    @Transactional
    public void updateWhiteToken(WhiteToken whiteToken, AuthToken authToken) {
        whiteToken.reIssuance(authToken.getAccessToken(), authToken.getRefreshToken());
    }

    public WhiteToken getWhiteToken(String accessToken, String refreshToken) {
        return whiteTokenRepository.findByAccessTokenAndRefreshToken(accessToken, refreshToken)
            .orElseThrow(() -> new UnauthorizedException("존재하지 않은 access 및 refresh 토큰"));
    }

}
