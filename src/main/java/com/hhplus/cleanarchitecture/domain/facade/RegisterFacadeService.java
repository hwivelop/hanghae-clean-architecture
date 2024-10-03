package com.hhplus.cleanarchitecture.domain.facade;

import com.hhplus.cleanarchitecture.domain.facade.dto.request.*;
import com.hhplus.cleanarchitecture.domain.facade.dto.response.*;
import com.hhplus.cleanarchitecture.domain.lecture.*;
import com.hhplus.cleanarchitecture.domain.lecture.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lecture.dto.response.*;
import com.hhplus.cleanarchitecture.domain.lecturehistory.*;
import com.hhplus.cleanarchitecture.domain.lecturehistory.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.response.*;
import com.hhplus.cleanarchitecture.domain.lectureitem.*;
import com.hhplus.cleanarchitecture.domain.lectureitem.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lectureitem.dto.response.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class RegisterFacadeService {

    private final LectureService lectureService;
    private final LectureItemService lectureItemService;
    private final LectureInventoryService lectureInventoryService;
    private final LectureHistoryService lectureHistoryService;

    /**
     * 강의 기본 정보 및 잔여 정보 저장
     */
    public LectureInfoDto createLectureInfo(LectureInfoCreateDto dto) {

        LectureDto lectureDto = lectureService.create(
                LectureCreateDto.builder()
                        .name(dto.getLectureName())
                        .memberId(dto.getMemberId())
                        .MemberName(dto.getMemberName())
                        .build()
        );

        LectureItemDto lectureItemDto = lectureItemService.create(
                LectureItemCreateDto.builder()
                        .lectureId(dto.getLectureId())
                        .capacity(dto.getCapacity())
                        .openDate(dto.getOpenDate())
                        .isClose(dto.getIsClose())
                        .build()
        );


        LectureInventoryDto lectureInventoryDto = lectureInventoryService.create(
                LectureInventoryCreateDto.builder()
                        .lectureId(lectureDto.getId())
                        .lectureItemId(lectureItemDto.getId())
                        .remainingSeats(lectureItemDto.getCapacity())
                        .build()
        );

        return LectureInfoDto.of(lectureDto, lectureItemDto, lectureInventoryDto);
    }

    /**
     * 유저의 강의 신청 히스토리 조회
     */
    public List<LectureApplyHistoryDto> getApplyHistoryByMemberId(long memberId) {

        List<LectureHistory> lectureHistories = lectureHistoryService.getByMemberId(memberId);

        return lectureHistories.stream()
                .map(history -> {
                    Long lectureId = history.getLectureId();
                    LectureDto lectureDto = lectureService.getOrThrow(lectureId);

                    Long lectureItemId = history.getLectureItemId();
                    LectureItemDto lectureItemDto = lectureItemService.getOrThrow(lectureItemId);

                    return LectureApplyHistoryDto.of(
                            lectureDto,
                            lectureItemDto,
                            history.getApplyStatus()
                    );
                }).collect(Collectors.toList());
    }

    public void applyLecture(LectureApplyDto dto) {

        long memberId = dto.getMemberId();
        long lectureId = dto.getLectureId();
        long lectureItemId = dto.getLectureItemId();

        lectureHistoryService.ifApplyHistoryExistThenThrow(memberId, lectureId, lectureItemId);

        lectureInventoryService.updateLectureInventoryInfo(dto);

        lectureHistoryService.create(
                LectureHistoryCreateDto.builder()
                        .memberId(memberId)
                        .lectureId(lectureId)
                        .lectureItemId(lectureItemId)
                        .applyStatus(dto.getApplyStatus())
                        .build()
        );
    }

    /**
     * 신청 가능한 강의 목록 조회
     */
    public Map<LocalDate, List<LectureInfoDto>> getAvailableLectureList() {

        List<LectureItemDto> availableItemDtoList = lectureItemService.getAvailable();

        return availableItemDtoList.stream()
                .collect(Collectors.groupingBy(
                        LectureItemDto::getOpenDate,
                        Collectors.mapping(it -> {
                            LectureDto lectureDto = lectureService.getOrThrow(it.getLectureId());
                            LectureInventoryDto lectureInventoryDto = lectureInventoryService.getOrThrow(it.getLectureId(), it.getId());
                            return LectureInfoDto.of(lectureDto, it, lectureInventoryDto);
                        }, Collectors.toList())
                ));
    }
}
