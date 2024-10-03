package com.hhplus.cleanarchitecture.was.register.dto.request;

import com.hhplus.cleanarchitecture.domain.facade.dto.request.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LectureInfoCreateRequest {

    @NotEmpty
    String lectureName;

    @NotNull
    Long memberId;

    @NotEmpty
    String memberName;

    @NotNull
    Long lectureId;

    @NotNull
    Integer capacity;

    @NotNull
    LocalDate openDate;

    @NotNull
    Boolean isClose;

    public LectureInfoCreateDto toReqeust() {

        return LectureInfoCreateDto.builder()
                .lectureName(lectureName)
                .memberId(memberId)
                .memberName(memberName)
                .lectureId(lectureId)
                .capacity(capacity)
                .openDate(openDate)
                .isClose(isClose)
                .build();
    }
}
