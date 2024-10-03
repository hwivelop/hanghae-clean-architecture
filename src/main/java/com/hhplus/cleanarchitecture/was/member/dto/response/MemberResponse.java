package com.hhplus.cleanarchitecture.was.member.dto.response;

import com.hhplus.cleanarchitecture.domain.member.*;
import com.hhplus.cleanarchitecture.domain.member.dto.request.*;
import com.hhplus.cleanarchitecture.domain.member.dto.response.*;
import lombok.*;

@Getter
@Builder
public class MemberResponse {

    Long id;

    String name;

    MemberType memberType;

    public static MemberResponse toResponse(MemberDto dto) {

        if (dto == null) return null;

        return MemberResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .memberType(dto.getMemberType())
                .build();
    }
}
