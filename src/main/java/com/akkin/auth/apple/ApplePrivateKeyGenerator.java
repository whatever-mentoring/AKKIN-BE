package com.akkin.auth.apple;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplePrivateKeyGenerator {

    @Value("${spring.oauth.apple.client_id}")
    private String clientId;

    @Value("${spring.oauth.apple.kid}")
    private String kid;

    @Value("${spring.oauth.apple.secret}")
    private String secret;

    @Value("${spring.oauth.apple.bundle}")
    private String bundle;

    public String generate() {
        Date expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant());
        Map<String, Object> jwtHeader = new HashMap<>();
        jwtHeader.put("kid", kid);
        jwtHeader.put("alg", "ES256");

        return Jwts.builder()
            .setHeaderParams(jwtHeader)
            .setIssuer(clientId)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(expirationDate)
            .setAudience("https://appleid.apple.com")
            .setSubject(bundle)
            .signWith(SignatureAlgorithm.ES256, kid)
            .compact();
    }
}
