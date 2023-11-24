package com.akkin.auth.apple;

import com.akkin.auth.apple.dto.AppleTokenResponse;
import com.akkin.auth.dto.request.AppleLoginRequest;
import com.akkin.auth.dto.request.AppleRevokeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RequiredArgsConstructor
@Component
public class AppleTokenService {

    private final ApplePrivateKeyGenerator privateKeyGenerator;

    @Value("${spring.oauth.apple.bundle}")
    private String bundle;

    public AppleTokenResponse getAppleToken(AppleRevokeRequest appleRevokeRequest) {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        String authUrl = "https://appleid.apple.com/auth/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", appleRevokeRequest.getAuthorizationCode());
        params.add("client_id", bundle);
        params.add("client_secret", privateKeyGenerator.generate());
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<AppleTokenResponse> response = restTemplate.postForEntity(authUrl, httpEntity, AppleTokenResponse.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("Apple Auth Token Error");
        }
    }
}
