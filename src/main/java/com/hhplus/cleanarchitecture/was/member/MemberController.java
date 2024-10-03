package com.hhplus.cleanarchitecture.was.member;

import com.hhplus.cleanarchitecture.domain.member.*;
import com.hhplus.cleanarchitecture.domain.member.dto.response.*;
import com.hhplus.cleanarchitecture.was.member.dto.request.*;
import com.hhplus.cleanarchitecture.was.member.dto.response.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * 유저 등록
     */
    @PostMapping
    public MemberResponse saveMember(@RequestBody MemberCreateRequest request) {
        log.info("name = {}, type = {}", request.getName(), request.getMemberType());

        MemberDto memberDto = memberService.create(request.toReqeust());

        return MemberResponse.toResponse(memberDto);
    }

    /**
     * 유저 조회
     */
    @GetMapping("/member/{memberId}")
    public MemberResponse getMember(@PathVariable("memberId") long memberId) {
        log.info("memberId = {}", memberId);

        MemberDto memberDto = memberService.get(memberId);

        return MemberResponse.toResponse(memberDto);
    }
}
