package com.hhplus.cleanarchitecture.domain.lectureitem;

import com.hhplus.cleanarchitecture.domain.lectureitem.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lectureitem.dto.response.*;
import jakarta.transaction.*;
import lombok.*;
import org.springframework.stereotype.*;

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
}
