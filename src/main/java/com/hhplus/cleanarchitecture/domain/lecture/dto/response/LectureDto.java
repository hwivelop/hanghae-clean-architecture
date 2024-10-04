package com.hhplus.cleanarchitecture.domain.lecture.dto.response;

import com.hhplus.cleanarchitecture.domain.lecture.*;
import com.hhplus.cleanarchitecture.domain.member.*;
import lombok.*;

@Getter
@Builder
public class LectureDto {

    Long id;

    String name;

    Long memberId;

    String memberName;

    public static LectureDto of(Lecture lecture) {

        return LectureDto.builder()
                .id(lecture.getId())
                .name(lecture.getName())
                .memberId(lecture.getMemberId())
                .memberName(lecture.getMemberName())
                .build();
    }
}
