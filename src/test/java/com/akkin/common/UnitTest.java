package com.akkin.common;

import com.akkin.auth.dto.AuthMember;
import com.akkin.auth.token.AuthTokenRepository;
import com.akkin.auth.token.AuthTokenService;
import com.akkin.gulbi.GulbiRepository;
import com.akkin.gulbi.create.GulbiCreateService;
import com.akkin.member.MemberRepository;
import com.akkin.member.MemberService;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UnitTest {

    @Autowired
    public MemberService memberService;

    @Autowired
    public MemberRepository memberRepository;

    @Autowired
    public GulbiCreateService gulbiCreateService;

    @Autowired
    public GulbiRepository gulbiRepository;

    @Autowired
    public AuthTokenRepository authTokenRepository;

    @Autowired
    public AuthTokenService authTokenService;
}
