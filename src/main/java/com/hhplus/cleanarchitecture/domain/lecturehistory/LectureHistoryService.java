package com.hhplus.cleanarchitecture.domain.lecturehistory;

import com.hhplus.cleanarchitecture.domain.lecturehistory.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lecturehistory.dto.response.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

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
                        .lectureItemId(dto.getLectureItemId())
                        .applyStatus(dto.getApplyStatus())
                        .build()
        );

        return LectureHistoryDto.of(lectureHistory);
    }

    @Transactional(readOnly = true)
    public List<LectureHistory> getByMemberId(long memberId) {

        return lectureHistoryRepository.findByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public void ifApplyHistoryExistThenThrow(Long memberId, Long lectureId, Long lectureItemId) {

        boolean isAlreadyApplied = lectureHistoryRepository.existsByMemberIdAndLectureIdAndLectureItemId(memberId, lectureId, lectureItemId);

        if (isAlreadyApplied) {
            throw new IllegalArgumentException("이미 신청한 강의입니다.");
        }
    }
}
