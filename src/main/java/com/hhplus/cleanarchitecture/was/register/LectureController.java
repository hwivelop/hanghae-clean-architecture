package com.hhplus.cleanarchitecture.was.register;

import com.hhplus.cleanarchitecture.domain.facade.*;
import com.hhplus.cleanarchitecture.domain.facade.dto.response.*;
import com.hhplus.cleanarchitecture.was.register.dto.request.*;
import com.hhplus.cleanarchitecture.was.register.dto.response.*;
import jakarta.validation.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class LectureController {

    private final RegisterFacadeService registerFacadeService;

    /**
     * 강의 등록
     */
    @PostMapping
    public ResponseEntity<LectureInfoResponse> saveLectureInfo(@Valid @RequestBody LectureInfoCreateRequest request) {
        log.info("request = {}", request);

        LectureInfoDto lectureInfoDto = registerFacadeService.createLectureInfo(
                request.toReqeust()
        );

        return ResponseEntity.ok(
                LectureInfoResponse.toResponse(lectureInfoDto)
        );
    }

    /**
     * 유저가 신청한 강의 조회
     */
    @GetMapping("/history/{memberId}")
    public ResponseEntity<List<LectureApplyHistoryDto>> getLectureInfo(@PathVariable("memberId") long memberId) {
        log.info("memberId = {}", memberId);

        return ResponseEntity.ok(
                registerFacadeService.getApplyHistoryByMemberId(memberId)
        );
    }

    /**
     * 강의 신청
     */
    @PostMapping("/apply")
    public ResponseEntity<Void> applyLecture(@Valid @RequestBody LectureApplyRequest request) {
        log.info("memberId = {}, status = {}", request.getMemberId(), request.getApplyStatus());

        registerFacadeService.applyLecture(request.toReqeust());

        return ResponseEntity.ok().build();
    }
}
