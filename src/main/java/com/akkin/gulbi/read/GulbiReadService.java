package com.akkin.gulbi.read;

import com.akkin.gulbi.Gulbi;
import com.akkin.gulbi.GulbiRepository;
import com.akkin.gulbi.create.dto.GulbiCreateResponse;
import com.akkin.gulbi.read.dto.GulbiReadResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GulbiReadService {

    private final GulbiRepository gulbiRepository;

    public GulbiReadResponses getGulbis(Long memberId) {
        List<Gulbi> gulbis = gulbiRepository.findByMemberId(memberId);
        List<GulbiCreateResponse> gulbiCreateResponses =
            gulbis.stream()
                .map(GulbiCreateResponse::new)
                .collect(Collectors.toList());

        return new GulbiReadResponses(gulbiCreateResponses);
    }
}
