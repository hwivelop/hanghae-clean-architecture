package com.hhplus.cleanarchitecture.was.register.dto.response;

import com.hhplus.cleanarchitecture.domain.facade.dto.response.*;
import lombok.*;

import java.time.*;

@Getter
@Builder
public class LectureInfoResponse {

    Long id;

    String lectureName;

    Long memberId;

    String memberName;

    Long lectureId;

    Integer capacity;

    LocalDate openDate;

    Boolean isClose;

    public static LectureInfoResponse toResponse(LectureInfoDto dto) {

        if (dto == null) return null;

        return LectureInfoResponse.builder()
                .id(dto.getLectureId())
                .lectureName(dto.getLectureName())
                .memberId(dto.getMemberId())
                .memberName(dto.getMemberName())
                .lectureId(dto.getLectureItemId())
                .capacity(dto.getCapacity())
                .openDate(dto.getOpenDate())
                .isClose(dto.getIsClose())
                .build();
    }
}
