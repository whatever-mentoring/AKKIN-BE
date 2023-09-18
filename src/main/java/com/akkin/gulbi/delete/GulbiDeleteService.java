package com.akkin.gulbi.delete;

import com.akkin.common.exception.GulbiNotFoundException;
import com.akkin.common.exception.GulbiNotOwnerException;
import com.akkin.gulbi.Gulbi;
import com.akkin.gulbi.GulbiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GulbiDeleteService {

    private final GulbiRepository gulbiRepository;

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
}
