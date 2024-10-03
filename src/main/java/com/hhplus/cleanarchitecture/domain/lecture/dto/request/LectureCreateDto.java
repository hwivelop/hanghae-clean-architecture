package com.hhplus.cleanarchitecture.domain.lecture.dto.request;

import lombok.*;

@Getter
@Builder
public class LectureCreateDto {

    String name;

    Long memberId;

    String MemberName;
}
