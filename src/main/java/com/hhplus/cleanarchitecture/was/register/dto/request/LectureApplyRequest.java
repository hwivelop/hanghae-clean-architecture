package com.hhplus.cleanarchitecture.was.register.dto.request;

import com.hhplus.cleanarchitecture.domain.facade.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lecturehistory.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureApplyRequest {

    @NotNull
    ApplyStatus applyStatus;

    @NotNull
    long memberId;

    @NotNull
    Long lectureId;

    @NotNull
    Long lectureItemId;

    public LectureApplyDto toReqeust() {

        return LectureApplyDto.builder()
                .memberId(memberId)
                .applyStatus(applyStatus)
                .lectureId(lectureId)
                .lectureItemId(lectureItemId)
                .build();
    }
}
