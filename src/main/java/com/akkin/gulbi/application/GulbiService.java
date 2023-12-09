package com.akkin.gulbi.application;

import com.akkin.gulbi.domain.GulbiCategory;
import com.akkin.gulbi.dto.response.GulbiResponse;
import com.akkin.gulbi.exception.GulbiNotFoundException;
import com.akkin.gulbi.exception.GulbiNotOwnerException;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.gulbi.dto.request.GulbiCreateForm;
import com.akkin.gulbi.dto.request.GulbiUpdateForm;
import com.akkin.gulbi.dto.response.GulbiListResponse;
import com.akkin.gulbi.persistence.GulbiRepository;
import com.akkin.member.domain.Member;
import com.akkin.member.application.MemberService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GulbiService {

    private final GulbiRepository gulbiRepository;
    private final MemberService memberService;

    public static int DEFAULT_GULBI_PAGE_SIZE = 10;

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

    public Gulbi getGulbiOrElseThrow(final Long gulbiId) {
        return gulbiRepository.findById(gulbiId)
            .orElseThrow(() -> new GulbiNotFoundException("존재하지 않은 아낀 항목"));
    }

    public GulbiListResponse getGublis(final Long memberId, final String category, final long lastId, final int pageSize) {
        final PageRequest pageRequest  = PageRequest.of(0, pageSize);
        final List<GulbiResponse> nextPage;

        if (category == null || category.isEmpty()) {
            nextPage = gulbiRepository.findGulbiResponseByMemberId(memberId, lastId, pageRequest);
        } else if (GulbiCategory.contains(category)) {
            final GulbiCategory gulbiCategory = GulbiCategory.valueOf(category);
            nextPage = gulbiRepository.findGulbiResponseByMemberIdAndCategory(memberId, gulbiCategory, lastId, pageRequest);
        } else {
            nextPage = Collections.emptyList();
        }
        return new GulbiListResponse(nextPage);
    }

    @Transactional
    public void update(final Long memberId, final Long gulbiId, final GulbiUpdateForm form) {
        final Gulbi gulbi = getGulbiOrElseThrow(gulbiId);
        if (!gulbi.isWriter(memberId)) {
            throw new GulbiNotOwnerException("작성자가 아닙니다.");
        }
        gulbi.updateGulbi(form);
    }

    @Transactional
    public void deleteRevokeMemberGulbi(final Member member) {
        gulbiRepository.deleteAllByMember(member);
    }
}
