package com.akkin.login;

import com.akkin.common.exception.AppleOauthLoginException;
import com.akkin.common.exception.OauthFailException;
import com.akkin.login.dto.AuthorizationResponse;
import com.akkin.login.dto.OauthMemberInfo;
import com.akkin.login.dto.TokenResponse;
import com.akkin.member.Member;
import com.akkin.member.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OauthService {

    @Value("${spring.oauth.apple.client_id}")
    private String appleKeyId;

    @Value("${spring.oauth.apple.key}")
    private String appleKeyIdFile;

    public void authCode(String code) throws Exception {
    }
}
