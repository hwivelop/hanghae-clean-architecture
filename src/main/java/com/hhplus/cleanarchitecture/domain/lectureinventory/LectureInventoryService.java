package com.hhplus.cleanarchitecture.domain.lectureinventory;

import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.response.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@RequiredArgsConstructor
@Service
public class LectureInventoryService {

    private final LectureInventoryRepository lectureInventoryRepository;

    @Transactional
    public LectureInventoryDto create(LectureInventoryCreateDto dto) {

        LectureInventory lectureInventory = lectureInventoryRepository.save(
                LectureInventory.builder()
                        .lectureId(dto.getLectureId())
                        .lectureItemId(dto.getLectureItemId())
                        .remainingSeats(dto.getRemainingSeats())
                        .build()
        );

        return LectureInventoryDto.of(lectureInventory);
    }

    @Transactional(readOnly = true)
    public LectureInventoryDto getOrThrow(Long lectureId, Long lectureItemId) {

        LectureInventory lectureInventory = lectureInventoryRepository.findByLectureIdAndLectureItemId(lectureId, lectureItemId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 강의 잔여 정보가 없습니다."));

        return LectureInventoryDto.of(lectureInventory);
    }
}
