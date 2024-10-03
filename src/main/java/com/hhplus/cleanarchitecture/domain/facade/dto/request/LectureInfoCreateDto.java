package com.hhplus.cleanarchitecture.domain.facade.dto.request;


import lombok.*;

import java.time.*;

@Getter
@Builder
public class LectureInfoCreateDto {

    String lectureName;

    Long memberId;

    String memberName;

    Long lectureId;

    Integer capacity;

    LocalDate openDate;

    Boolean isClose;
}
