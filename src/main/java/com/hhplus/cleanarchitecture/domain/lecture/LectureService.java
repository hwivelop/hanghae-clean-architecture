package com.hhplus.cleanarchitecture.domain.lecture;

import com.hhplus.cleanarchitecture.domain.lecture.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lecture.dto.response.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@RequiredArgsConstructor
@Service
public class LectureService {

    private final LectureRepository lectureRepository;

    @Transactional
    public LectureDto create(LectureCreateDto dto) {

        Lecture lecture = lectureRepository.save(
                Lecture.builder()
                        .name(dto.getName())
                        .memberId(dto.getMemberId())
                        .memberName(dto.getMemberName())
                        .build()
        );

        return LectureDto.of(lecture);
    }

    @Transactional(readOnly = true)
    public LectureDto getOrThrow(Long lectureId) {

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 강의 정보가 없습니다."));

        return LectureDto.of(lecture);
    }
}
