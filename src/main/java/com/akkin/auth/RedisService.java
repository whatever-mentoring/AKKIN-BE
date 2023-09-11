package com.akkin.auth;

import com.akkin.auth.dto.AuthToken;
import com.akkin.common.exception.ExpireRefreshTokenException;
import com.akkin.login.dto.AuthMember;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RedisService {

    private final RedisTemplate<String, AuthMember> redisTemplate;

    private final WhiteTokenRepository whiteTokenRepository;

    private final int ACCESS_TOKEN_EXPIRE = 60 * 60; // 1시간

    @Transactional
    public AuthToken setAuthMember(AuthMember authMember) {
        String accessToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();
        whiteTokenRepository.save(new WhiteToken(authMember.getId(), accessToken, refreshToken));
        redisTemplate.opsForValue().set(accessToken, authMember, ACCESS_TOKEN_EXPIRE, TimeUnit.SECONDS);
        return new AuthToken(accessToken, refreshToken);
    }

    public AuthMember getAuthMember(String accessToken) {
        return redisTemplate.opsForValue().get(accessToken);
    }

    @Transactional
    public AuthToken checkRefreshToken(String refreshToken) {
        WhiteToken whiteToken = whiteTokenRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new ExpireRefreshTokenException(ExpireRefreshTokenException.NON_EXISTENT_TOKEN_MSG));

        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(whiteToken.getExpiredAt())) {
            whiteTokenRepository.delete(whiteToken);
            throw new ExpireRefreshTokenException(ExpireRefreshTokenException.EXPIRED_TOKEN_MSG);
        }

        String newAccessToken = generateToken();
        String newRefreshToken = generateToken();
        whiteToken.reIssuance(newAccessToken, newRefreshToken);

        return new AuthToken(newAccessToken, newRefreshToken);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

}
