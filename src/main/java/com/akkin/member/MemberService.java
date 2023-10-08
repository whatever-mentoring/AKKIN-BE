package com.akkin.member;

import com.akkin.common.exception.MemberNotFoundException;
import com.akkin.auth.login.apple.dto.AppleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveOrUpdateMember(AppleUser appleUser) {
        return memberRepository.findByEmail(appleUser.getEmail())
            .map(member -> {
                member.updateLoginTime();
                return member;
            })
            .orElseGet(() -> memberRepository.save(new Member(appleUser)));
    }

    public Member findMemberOrElseThrow(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 멤버입니다. : " + memberId));
    }
}
