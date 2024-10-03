package com.hhplus.cleanarchitecture.domain.lectureinventory;

import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.response.*;
import jakarta.transaction.*;
import lombok.*;
import org.springframework.stereotype.*;

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
}
