package com.akkin.auth;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhiteTokenRepository extends JpaRepository<WhiteToken, Long> {

    Optional<WhiteToken> findByRefreshToken(String refreshToken);
}
