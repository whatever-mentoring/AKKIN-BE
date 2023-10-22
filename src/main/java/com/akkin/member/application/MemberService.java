package com.akkin.member.application;

import com.akkin.member.persistence.MemberRepository;
import com.akkin.member.domain.Member;
import com.akkin.member.exception.MemberNotFoundException;
import com.akkin.auth.apple.dto.AppleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveOrUpdateMember(final AppleUser appleUser) {
        return memberRepository.findByEmail(appleUser.getEmail())
            .map(member -> {
                member.updateLoginTime();
                return member;
            })
            .orElseGet(() -> memberRepository.save(new Member(appleUser)));
    }

    public Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 멤버입니다. : " + memberId));
    }
}
