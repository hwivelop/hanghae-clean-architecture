package com.hhplus.cleanarchitecture.domain.facade.dto.response;

import com.hhplus.cleanarchitecture.domain.lecture.dto.response.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.response.*;
import com.hhplus.cleanarchitecture.domain.lectureitem.dto.response.*;
import lombok.*;

import java.time.*;

@Getter
@Builder
public class LectureInfoDto {

    Long lectureId;

    String lectureName;

    Long memberId;

    String memberName;

    Long lectureItemId;

    Integer capacity;

    LocalDate openDate;

    Boolean isClose;

    Long lectureInventoryId;

    public static LectureInfoDto of(
            LectureDto lectureDto,
            LectureItemDto lectureItemDto,
            LectureInventoryDto lectureInventoryDto
    ) {

        return LectureInfoDto.builder()
                .lectureId(lectureDto.getId())
                .lectureName(lectureDto.getName())
                .memberId(lectureDto.getMemberId())
                .memberName(lectureDto.getMemberName())
                .lectureItemId(lectureItemDto.getId())
                .capacity(lectureItemDto.getCapacity())
                .openDate(lectureItemDto.getOpenDate())
                .isClose(lectureItemDto.getIsClose())
                .lectureInventoryId(lectureInventoryDto.getId())
                .build();
    }
}
