package com.akkin.gulbi.application;

import com.akkin.common.exception.GulbiNotFoundException;
import com.akkin.common.exception.GulbiNotOwnerException;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.gulbi.persistence.GulbiRepository;
import com.akkin.gulbi.dto.GulbiCreateForm;
import com.akkin.gulbi.dto.GulbiCreateResponse;
import com.akkin.gulbi.dto.GulbiReadResponses;
import com.akkin.gulbi.dto.GulbiUpdateForm;
import com.akkin.gulbi.dto.GulbiUpdateResponse;
import com.akkin.member.Member;
import com.akkin.member.MemberService;
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

    /**
     * 아낀 항목 생성
     */
    @Transactional
    public GulbiCreateResponse createGulbi(Long memberId, GulbiCreateForm form) {
        Member member = memberService.findMember(memberId);
        Gulbi gulbi = gulbiRepository.save(form.dtoToEntity(member));
        return new GulbiCreateResponse(gulbi);
    }

    /**
     * 아낀 항목 삭제
     */
    @Transactional
    public void deleteGulbi(Long memberId, Long gulbiId) {
        Gulbi gulbi = getGulbiOrElseThrow(gulbiId);
        checkOwnerOrElseThrow(gulbi, memberId);
        gulbiRepository.delete(gulbi);
    }

    private Gulbi getGulbiOrElseThrow(Long gulbiId) {
        Optional<Gulbi> gulbi = gulbiRepository.findById(gulbiId);
        if (gulbi.isEmpty()) {
            throw new GulbiNotFoundException("존재하지 않은 아낀 항목");
        }
        return gulbi.get();
    }

    private void checkOwnerOrElseThrow(Gulbi gulbi, Long memberId) {
        if (gulbi.getMember().getId() != memberId) {
            throw new GulbiNotOwnerException("작성자가 아닙니다.");
        }
    }

    /**
     * 작성한 아낀 항목 가져오기
     */
    public GulbiReadResponses getGulbis(Long memberId) {
        List<Gulbi> gulbis = gulbiRepository.findByMemberId(memberId);
        List<GulbiCreateResponse> gulbiCreateResponses =
            gulbis.stream()
                .map(GulbiCreateResponse::new)
                .collect(Collectors.toList());

        return new GulbiReadResponses(gulbiCreateResponses);
    }

    /**
     * 아낀 항목 업데이트
     */
    @Transactional
    public GulbiUpdateResponse updateGulbi(Long memberId, Long gulbiId, GulbiUpdateForm form) {
        Gulbi gulbi = getGulbiOrElseThrow(gulbiId);
        checkOwnerOrElseThrow(gulbi, memberId);
        gulbi.updateGulbi(form);
        return new GulbiUpdateResponse(gulbi);
    }

}
