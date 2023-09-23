package com.akkin.login.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class AppleOAuthUserProvider {

    private static final String JWT_SPLIT = ".";
    private static final int HEADER_INDEX = 0;

    private final ObjectMapper objectMapper;

    public void extractToken(String appleToken) {
        Map<String, String> appleTokenHeader = tokenDecoder(appleToken);
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
}
