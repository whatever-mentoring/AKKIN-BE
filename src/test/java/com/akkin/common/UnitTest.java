package com.akkin.common;

import com.akkin.gulbi.GulbiRepository;
import com.akkin.gulbi.GulbiService;
import com.akkin.member.MemberRepository;
import com.akkin.member.MemberService;
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
    public GulbiService gulbiService;

    @Autowired
    public GulbiRepository gulbiRepository;
}