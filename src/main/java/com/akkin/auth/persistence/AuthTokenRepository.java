package com.akkin.auth.persistence;

import com.akkin.auth.domain.AuthToken;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    Optional<AuthToken> findByAccessTokenAndRefreshToken(String accessToken, String refreshToken);

    List<AuthToken> findAllByMemberId(Long memberId);

    Optional<AuthToken> findByMemberId(Long memberId);

    void deleteByMemberId(Long memberId);
}
