package com.hhplus.cleanarchitecture.domain.lectureinventory.dto.response;

import com.hhplus.cleanarchitecture.domain.lectureinventory.*;
import lombok.*;

@Getter
@Builder
public class LectureInventoryDto {

    Long id;

    Long lectureId;

    Long lectureItemId;

    Integer remainingSeats;


    public static LectureInventoryDto of(LectureInventory lectureInventory) {

        return LectureInventoryDto.builder()
                .id(lectureInventory.getId())
                .lectureId(lectureInventory.getLectureId())
                .lectureItemId(lectureInventory.getLectureItemId())
                .remainingSeats(lectureInventory.getRemainingSeats())
                .build();
    }
}
