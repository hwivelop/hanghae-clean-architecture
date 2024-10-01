package com.hhplus.cleanarchitecture.domain.member.dto.request;

import com.hhplus.cleanarchitecture.domain.member.*;
import lombok.*;

@Getter
@Builder
public class MemberCreateDto {

    String name;

    MemberType memberType;
}
