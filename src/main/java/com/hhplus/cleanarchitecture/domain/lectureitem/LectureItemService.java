package com.hhplus.cleanarchitecture.domain.lectureitem;

import com.hhplus.cleanarchitecture.domain.lectureitem.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lectureitem.dto.response.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@RequiredArgsConstructor
@Service
public class LectureItemService {

    private final LectureItemRepository lectureItemRepository;

    @Transactional
    public LectureItemDto create(LectureItemCreateDto dto) {

        LectureItem lectureItem = lectureItemRepository.save(
                LectureItem.builder()
                        .lectureId(dto.getLectureId())
                        .capacity(dto.getCapacity())
                        .openDate(dto.getOpenDate())
                        .isClose(dto.getIsClose())
                        .build()
        );

        return LectureItemDto.of(lectureItem);
    }

    @Transactional(readOnly = true)
    public LectureItemDto getOrThrow(Long lectureItemId) {

        LectureItem lectureItem = lectureItemRepository.findById(lectureItemId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 강의 상세 정보가 없습니다."));

        return LectureItemDto.of(lectureItem);
    }
}
