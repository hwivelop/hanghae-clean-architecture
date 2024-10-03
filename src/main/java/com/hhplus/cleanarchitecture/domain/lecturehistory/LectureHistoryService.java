package com.hhplus.cleanarchitecture.domain.lecturehistory;

import com.hhplus.cleanarchitecture.domain.lecturehistory.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lecturehistory.dto.response.*;
import jakarta.transaction.*;
import lombok.*;
import org.springframework.stereotype.*;

@RequiredArgsConstructor
@Service
public class LectureHistoryService {

    private final LectureHistoryRepository lectureHistoryRepository;

    @Transactional
    public LectureHistoryDto create(LectureHistoryCreateDto dto) {

        LectureHistory lectureHistory = lectureHistoryRepository.save(
                LectureHistory.builder()
                        .memberId(dto.getMemberId())
                        .lectureId(dto.getLectureId())
                        .applyStatus(dto.getApplyStatus())
                        .build()
        );

        return LectureHistoryDto.of(lectureHistory);
    }
}
