package com.hhplus.cleanarchitecture.was.member.dto.request;

import com.hhplus.cleanarchitecture.domain.member.*;
import com.hhplus.cleanarchitecture.domain.member.dto.request.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateRequest {

    String name;

    MemberType memberType;

    public MemberCreateDto toReqeust() {

        return MemberCreateDto.builder()
                .name(this.name)
                .memberType(this.memberType)
                .build();
    }
}
