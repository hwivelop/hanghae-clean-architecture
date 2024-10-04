package com.hhplus.cleanarchitecture.domain.lectureitem.dto.request;

import lombok.*;

import java.time.*;

@Getter
@Builder
public class LectureItemCreateDto {

    Long lectureId;

    Integer capacity;

    LocalDate openDate;

    Boolean isClose;
}
