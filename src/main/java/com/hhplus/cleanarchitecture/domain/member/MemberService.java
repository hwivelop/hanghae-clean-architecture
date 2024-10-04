package com.hhplus.cleanarchitecture.domain.member;

import com.hhplus.cleanarchitecture.domain.member.dto.request.*;
import com.hhplus.cleanarchitecture.domain.member.dto.response.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.stream.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 유저 등록
     */
    @Transactional
    public MemberDto create(final MemberCreateDto dto) {

        Member member = memberRepository.save(
                Member.builder()
                        .name(dto.getName())
                        .memberType(dto.getMemberType())
                        .build()
        );

        return MemberDto.of(member);
    }

    /**
     * 유저 조회
     */
    @Transactional(readOnly = true)
    public MemberDto get(final Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 정보입니다."));

        return MemberDto.of(member);
    }
    @Transactional(readOnly = true)
    public List<MemberDto> getByAll() {

        List<Member> member = memberRepository.findAll();

        return member.stream()
                .map(MemberDto::of)
                .collect(Collectors.toList());
    }
}
