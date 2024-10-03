package com.hhplus.cleanarchitecture.domain.lecture;

import com.hhplus.cleanarchitecture.domain.lecture.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lecture.dto.response.*;
import jakarta.transaction.*;
import lombok.*;
import org.springframework.stereotype.*;

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
}
