package com.akkin.gulbi.application;

import com.akkin.gulbi.exception.GulbiNotFoundException;
import com.akkin.gulbi.exception.GulbiNotOwnerException;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.gulbi.dto.request.GulbiCreateForm;
import com.akkin.gulbi.dto.request.GulbiUpdateForm;
import com.akkin.gulbi.dto.response.GulbiCreateResponse;
import com.akkin.gulbi.dto.response.GulbiResponses;
import com.akkin.gulbi.dto.response.GulbiUpdateResponse;
import com.akkin.gulbi.persistence.GulbiRepository;
import com.akkin.member.domain.Member;
import com.akkin.member.application.MemberService;
import java.util.List;
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
    public void create(final Long memberId, final GulbiCreateForm form) {
        final Member member = memberService.findMember(memberId);
        gulbiRepository.save(form.dtoToEntity(member));
    }

    @Transactional
    public void delete(final Long memberId, final Long gulbiId) {
        final Gulbi gulbi = getGulbiOrElseThrow(gulbiId);
        if (!gulbi.isWriter(memberId)) {
            throw new GulbiNotOwnerException("작성자가 아닙니다.");
        }
        gulbiRepository.delete(gulbi);
    }

    private Gulbi getGulbiOrElseThrow(final Long gulbiId) {
        return gulbiRepository.findById(gulbiId)
            .orElseThrow(() -> new GulbiNotFoundException("존재하지 않은 아낀 항목"));
    }

    public GulbiResponses getGulbis(final Long memberId) {
        final List<Gulbi> gulbis = gulbiRepository.findByMemberId(memberId);
        final List<GulbiCreateResponse> gulbiCreateResponses =
            gulbis.stream()
                .map(GulbiCreateResponse::new)
                .collect(Collectors.toList());

        return new GulbiResponses(gulbiCreateResponses);
    }

    @Transactional
    public GulbiUpdateResponse update(final Long memberId, final Long gulbiId,
        final GulbiUpdateForm form) {
        final Gulbi gulbi = getGulbiOrElseThrow(gulbiId);
        if (!gulbi.isWriter(memberId)) {
            throw new GulbiNotOwnerException("작성자가 아닙니다.");
        }
        gulbi.updateGulbi(form);
        return new GulbiUpdateResponse(gulbi);
    }
}
