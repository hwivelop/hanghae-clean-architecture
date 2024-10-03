package com.hhplus.cleanarchitecture.domain.lectureitem.dto.response;

import com.hhplus.cleanarchitecture.domain.lectureinventory.*;
import com.hhplus.cleanarchitecture.domain.lectureitem.*;
import lombok.*;

import java.time.*;

@Getter
@Builder
public class LectureItemDto {

    Long id;

    Long lectureId;

    Integer capacity;

    LocalDate openDate;

    Boolean isClose;


    public static LectureItemDto of(LectureItem lectureItem) {

        return LectureItemDto.builder()
                .id(lectureItem.getId())
                .lectureId(lectureItem.getLectureId())
                .capacity(lectureItem.getCapacity())
                .openDate(lectureItem.getOpenDate())
                .isClose(lectureItem.getIsClose())
                .build();
    }
}
