package com.hhplus.cleanarchitecture.domain.member;

import com.hhplus.cleanarchitecture.domain.member.dto.request.*;
import com.hhplus.cleanarchitecture.domain.member.dto.response.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import static com.hhplus.cleanarchitecture.domain.member.MemberType.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("유저를 등록한다.")
    void createMember() {

        //given
        final String name = "김성휘";
        final MemberType memberType = STUDENT;

        final MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                .name(name)
                .memberType(memberType)
                .build();

        //when
        MemberDto memberDto = memberService.create(memberCreateDto);

        //then
        assertThat(memberDto.getName()).isEqualTo(name);
        assertThat(memberDto.getMemberType()).isEqualTo(memberType);
    }

    @Test
    @DisplayName("유저를 조회한다.")
    void getMember() {

        //given
        final Long memberId = 1L;
        final String name = "김성휘";
        final MemberType memberType = STUDENT;

        final MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                .name(name)
                .memberType(memberType)
                .build();

        memberService.create(memberCreateDto);

        //when
        MemberDto memberDto = memberService.get(memberId);

        //then
        assertThat(memberDto.getName()).isEqualTo(name);
        assertThat(memberDto.getMemberType()).isEqualTo(memberType);
    }

    @Test
    @DisplayName("존재하지 않는 유저를 조회하면 예외가 발생한다.")
    void getMemberUnknownId() {

        //given
        final Long UnknownMemberId = 2L;
        final String name = "김성휘";
        final MemberType memberType = STUDENT;

        final MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                .name(name)
                .memberType(memberType)
                .build();

        memberService.create(memberCreateDto);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.get(UnknownMemberId);
        });

        //then
        assertEquals("존재하지 않는 유저 정보입니다.", exception.getMessage());
    }
}