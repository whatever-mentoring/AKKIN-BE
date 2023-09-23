package com.akkin.login.apple;

import com.akkin.login.apple.dto.ApplePublicKey;
import com.akkin.login.apple.dto.ApplePublicKeys;
import com.akkin.login.apple.dto.AppleUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class AppleOAuthUserProvider {

    private static final String JWT_SPLIT = "\\.";
    private static final int HEADER_INDEX = 0;

    private static final String SIGN_ALGORITHM_HEADER = "alg";
    private static final String KEY_ID_HEADER = "kid";
    private static final int POSITIVE_SIGN_NUMBER = 1;

    private final ObjectMapper objectMapper;

    private final AppleClient appleClient;

    public AppleUser extractToken(String appleToken) {
        Map<String, String> appleTokenHeader = tokenDecoder(appleToken);
        ApplePublicKeys applePublicKeys = appleClient.getApplePublicKeys();
        PublicKey publicKey = applePublicKeyGenerator(appleTokenHeader, applePublicKeys);
        Claims claims = extractClaims(appleToken, publicKey);
        return new AppleUser(claims.getSubject(), claims.get("email", String.class));
    }

    private Map<String, String> tokenDecoder(String appleToken) {
        try {
            String encodedHeader = appleToken.split(JWT_SPLIT)[HEADER_INDEX];
            String decodedHeader = new String(Base64.getUrlDecoder().decode(encodedHeader));
            return objectMapper.readValue(decodedHeader, Map.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException("Invalid Header");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid base64 decode");
        }
    }

    private PublicKey applePublicKeyGenerator(Map<String, String> appleTokenHeader, ApplePublicKeys applePublicKeys) {
        ApplePublicKey applePublicKey =
                applePublicKeys.getMatchingKey(appleTokenHeader.get(SIGN_ALGORITHM_HEADER),
                        appleTokenHeader.get(KEY_ID_HEADER));
        return generatePublicKey(applePublicKey);
    }

    private PublicKey generatePublicKey(ApplePublicKey applePublicKey) {
        byte[] nBytes = Base64.getUrlDecoder().decode(applePublicKey.getN());
        byte[] eBytes = Base64.getUrlDecoder().decode(applePublicKey.getE());

        BigInteger n = new BigInteger(POSITIVE_SIGN_NUMBER, nBytes);
        BigInteger e = new BigInteger(POSITIVE_SIGN_NUMBER, eBytes);
        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(n, e);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(applePublicKey.getKty());
            return keyFactory.generatePublic(rsaPublicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw new RuntimeException("잘못된 애플 키");
        }
    }

    private Claims extractClaims(String appleToken, PublicKey publicKey) {
        try {
            JwtParser jwtParser = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build();
            return jwtParser.parseClaimsJws(appleToken).getBody();
        } catch (Exception e) {
            // todo: 애플 로그인 성공하면 exception 나누기
            throw new RuntimeException("애플 사용자 claim 추출 오류");
        }
    }
}
