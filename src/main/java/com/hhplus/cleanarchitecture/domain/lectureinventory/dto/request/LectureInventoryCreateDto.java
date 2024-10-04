package com.hhplus.cleanarchitecture.domain.lectureinventory.dto.request;

import lombok.*;

@Getter
@Builder
public class LectureInventoryCreateDto {

    Long lectureId;

    Long lectureItemId;

    Integer remainingSeats;
}
