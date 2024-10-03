package com.hhplus.cleanarchitecture.domain.member.dto.response;

import com.hhplus.cleanarchitecture.domain.member.*;
import lombok.*;

@Getter
@Builder
public class MemberDto {

    Long id;

    String name;

    MemberType memberType;

    public static MemberDto of(Member member) {

        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .memberType(member.getMemberType())
                .build();
    }
}
