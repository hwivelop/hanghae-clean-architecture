package com.hhplus.cleanarchitecture.domain.lecturehistory.dto.response;

import com.hhplus.cleanarchitecture.domain.lecture.*;
import com.hhplus.cleanarchitecture.domain.lecturehistory.*;
import lombok.*;

@Getter
@Builder
public class LectureHistoryDto {

    Long id;

    Long memberId;

    Long lectureId;

    Long lectureItemId;

    ApplyStatus applyStatus;


    public static LectureHistoryDto of(LectureHistory lectureHistory) {

        return LectureHistoryDto.builder()
                .id(lectureHistory.getId())
                .memberId(lectureHistory.getMemberId())
                .lectureId(lectureHistory.getLectureId())
                .lectureItemId(lectureHistory.getLectureItemId())
                .applyStatus(lectureHistory.getApplyStatus())
                .build();
    }
}
