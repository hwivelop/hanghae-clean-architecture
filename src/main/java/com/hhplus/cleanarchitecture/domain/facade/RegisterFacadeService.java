package com.hhplus.cleanarchitecture.domain.facade;

import com.hhplus.cleanarchitecture.domain.facade.dto.request.*;
import com.hhplus.cleanarchitecture.domain.facade.dto.response.*;
import com.hhplus.cleanarchitecture.domain.lecture.*;
import com.hhplus.cleanarchitecture.domain.lecture.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lecture.dto.response.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.response.*;
import com.hhplus.cleanarchitecture.domain.lectureitem.*;
import com.hhplus.cleanarchitecture.domain.lectureitem.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lectureitem.dto.response.*;
import lombok.*;
import org.springframework.stereotype.*;

@RequiredArgsConstructor
@Service
public class RegisterFacadeService {

    private final LectureService lectureService;
    private final LectureItemService lectureItemService;
    private final LectureInventoryService lectureInventoryService;

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
}
