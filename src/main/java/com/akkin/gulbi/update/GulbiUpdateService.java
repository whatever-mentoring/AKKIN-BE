package com.akkin.gulbi.update;

import com.akkin.common.exception.GulbiNotFoundException;
import com.akkin.common.exception.GulbiNotOwnerException;
import com.akkin.gulbi.Gulbi;
import com.akkin.gulbi.GulbiRepository;
import com.akkin.gulbi.update.dto.GulbiUpdateForm;
import com.akkin.gulbi.update.dto.GulbiUpdateResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GulbiUpdateService {

    private final GulbiRepository gulbiRepository;

    @Transactional
    public GulbiUpdateResponse updateGulbi(Long memberId, Long gulbiId, GulbiUpdateForm form) {
        Gulbi gulbi = getGulbiOrElseThrow(gulbiId);
        checkOwnerOrElseThrow(gulbi, memberId);
        gulbi.updateGulbi(form);
        return new GulbiUpdateResponse(gulbi);
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
}
