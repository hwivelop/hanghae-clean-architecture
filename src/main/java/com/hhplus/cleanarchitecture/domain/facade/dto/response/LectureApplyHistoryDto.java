package com.hhplus.cleanarchitecture.domain.facade.dto.response;

import com.hhplus.cleanarchitecture.domain.lecture.dto.response.*;
import com.hhplus.cleanarchitecture.domain.lecturehistory.*;
import com.hhplus.cleanarchitecture.domain.lectureitem.dto.response.*;
import lombok.*;

import java.time.*;

@Getter
@Builder
public class LectureApplyHistoryDto {

    Long lectureId;

    String lectureName;

    Long memberId;

    String memberName;

    Long lectureItemId;

    Integer capacity;

    LocalDate openDate;

    ApplyStatus applyStatus;

    public static LectureApplyHistoryDto of(
            LectureDto lectureDto,
            LectureItemDto lectureItemDto,
            ApplyStatus applyStatus
    ) {

        return LectureApplyHistoryDto.builder()
                .lectureId(lectureDto.getId())
                .lectureName(lectureDto.getName())
                .memberId(lectureDto.getMemberId())
                .memberName(lectureDto.getMemberName())
                .lectureItemId(lectureItemDto.getId())
                .capacity(lectureItemDto.getCapacity())
                .openDate(lectureItemDto.getOpenDate())
                .applyStatus(applyStatus)
                .build();
    }
}
