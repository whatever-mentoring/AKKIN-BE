package com.akkin.auth.whitelist;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhiteTokenRepository extends JpaRepository<WhiteToken, Long> {

    Optional<WhiteToken> findByMemberIdAndRefreshToken(Long memberId, String refreshToken);

    Optional<WhiteToken> findByAccessTokenAndRefreshToken(String accessToken, String refreshToken);
}
