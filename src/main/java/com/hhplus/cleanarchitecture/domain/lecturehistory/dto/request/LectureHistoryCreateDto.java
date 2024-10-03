package com.hhplus.cleanarchitecture.domain.lecturehistory.dto.request;

import com.hhplus.cleanarchitecture.domain.lecturehistory.*;
import lombok.*;

@Getter
@Builder
public class LectureHistoryCreateDto {

    String name;

    Long memberId;

    Long lectureId;

    Long lectureItemId;

    ApplyStatus applyStatus;
}
