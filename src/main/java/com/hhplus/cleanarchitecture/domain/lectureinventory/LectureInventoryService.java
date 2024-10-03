package com.hhplus.cleanarchitecture.domain.lectureinventory;

import com.hhplus.cleanarchitecture.domain.facade.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.response.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Slf4j
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

    @Transactional
    public LectureInventoryDto updateLectureInventoryInfo(LectureApplyDto dto) {

        // 비관적 락을 사용하여 잔여 좌석을 가져옴
        LectureInventory lectureInventory = lectureInventoryRepository.findByLectureIdAndLectureItemIdWithLock(dto.getLectureId(), dto.getLectureItemId())
                .orElseThrow(() -> new IllegalArgumentException("잔여 좌석 정보를 조회할 수 없습니다.")
                );

        lectureInventory.remainingSeatsZeroThenThrow();

        lectureInventory.updateRemainingSeats(dto.getApplyStatus());

        return LectureInventoryDto.of(
                lectureInventoryRepository.save(lectureInventory)
        );
    }

}
