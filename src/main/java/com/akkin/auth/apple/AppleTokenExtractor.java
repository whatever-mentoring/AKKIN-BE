package com.akkin.auth.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.security.PublicKey;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

@Slf4j
@RequiredArgsConstructor
@Component
public class AppleTokenExtractor {

    private static final String IDENTITY_TOKEN_VALUE_DELIMITER = "\\.";
    private static final int HEADER_INDEX = 0;

    private final ObjectMapper objectMapper;

    public Map<String, String> extractHeader(String appleToken) {
        try {
            log.info("appleToken : "  + appleToken);
            String encodedHeader = appleToken.split(IDENTITY_TOKEN_VALUE_DELIMITER)[HEADER_INDEX];
            log.info("encodedHeader : "  + encodedHeader);
            String decodedHeader = new String(Base64Utils.decodeFromString(encodedHeader));
            return objectMapper.readValue(decodedHeader, Map.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException("Invalid Header");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid base64 decode");
        }
    }

    public Claims extractClaims(String appleToken, PublicKey publicKey) {
        try {
            return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(appleToken)
                .getPayload();
        } catch (Exception e) {
            // todo: 애플 로그인 성공하면 exception 나누기
            throw new RuntimeException("애플 사용자 claim 추출 오류");
        }
    }

}
