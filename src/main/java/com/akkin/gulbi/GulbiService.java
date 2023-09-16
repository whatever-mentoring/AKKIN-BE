package com.akkin.gulbi;

import com.akkin.common.exception.GulbiNotFoundException;
import com.akkin.common.exception.GulbiNotOwnerException;
import com.akkin.gulbi.dto.GulbiCreateForm;
import com.akkin.gulbi.dto.GulbiCreateResponse;
import com.akkin.gulbi.dto.GulbiCreateResponses;
import com.akkin.member.Member;
import com.akkin.member.MemberService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GulbiService {

    private final GulbiRepository gulbiRepository;
    private final MemberService memberService;

    @Transactional
    public GulbiCreateResponse createGulbi(Long memberId, GulbiCreateForm form) {
        Member member = memberService.findMemberOrElseThrow(memberId);
        Gulbi gulbi = gulbiRepository.save(form.dtoToEntity(member));
        return new GulbiCreateResponse(gulbi);
    }

    public GulbiCreateResponses getGulbis(Long memberId) {
        List<Gulbi> gulbis = gulbiRepository.findByMemberId(memberId);
        List<GulbiCreateResponse> gulbiCreateResponses =
            gulbis.stream()
                .map(GulbiCreateResponse::new)
                .collect(Collectors.toList());

        return new GulbiCreateResponses(gulbiCreateResponses);
    }
}
