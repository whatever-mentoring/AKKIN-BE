package com.akkin.gulbi;

import com.akkin.gulbi.dto.GulbiCreateForm;
import com.akkin.gulbi.dto.GulbiCreateResponse;
import com.akkin.member.Member;
import com.akkin.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GulbiService {

    private final GulbiRepository gulbiRepository;
    private final MemberService memberService;

    public GulbiCreateResponse createGulbi(Long memberId, GulbiCreateForm form) {
        Member member = memberService.findMemberOrElseThrow(memberId);
        Gulbi gulbi = gulbiRepository.save(form.dtoToEntity(member));
        return new GulbiCreateResponse(gulbi);
    }
}
