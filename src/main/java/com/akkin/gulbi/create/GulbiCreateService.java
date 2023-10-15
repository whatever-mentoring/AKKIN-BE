package com.akkin.gulbi.create;

import com.akkin.gulbi.Gulbi;
import com.akkin.gulbi.GulbiRepository;
import com.akkin.gulbi.create.dto.GulbiCreateForm;
import com.akkin.gulbi.create.dto.GulbiCreateResponse;
import com.akkin.member.Member;
import com.akkin.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class GulbiCreateService {

    private final GulbiRepository gulbiRepository;
    private final MemberService memberService;

    @Transactional
    public GulbiCreateResponse createGulbi(Long memberId, GulbiCreateForm form) {
        Member member = memberService.findMember(memberId);
        Gulbi gulbi = gulbiRepository.save(form.dtoToEntity(member));
        return new GulbiCreateResponse(gulbi);
    }
}
