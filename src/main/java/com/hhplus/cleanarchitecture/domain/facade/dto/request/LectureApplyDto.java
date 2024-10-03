
package com.hhplus.cleanarchitecture.domain.facade.dto.request;


import com.hhplus.cleanarchitecture.domain.lecturehistory.*;
import lombok.*;

@Getter
@Builder
public class LectureApplyDto {

    long memberId;

    ApplyStatus applyStatus;

    Long lectureId;

    Long lectureItemId;
}
