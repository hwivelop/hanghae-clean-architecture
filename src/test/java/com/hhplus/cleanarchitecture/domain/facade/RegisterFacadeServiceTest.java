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
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.time.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterFacadeServiceTest {

    @Mock
    private LectureService lectureService;

    @Mock
    private LectureInventoryService lectureInventoryService;

    @Mock
    private LectureItemService lectureItemService;

    @InjectMocks
    private RegisterFacadeService registerFacadeService;

    @Test
    @DisplayName("강의 기본 정보 및 잔여정보를 저장한다.")
    void createLectureInfo() {

        //given
        Long lectureId = 1L;
        String lectureName = "클린아키텍쳐";
        Long lectureItemId = 1L;
        Long lectureInventoryId = 1L;

        LocalDate openDate = LocalDate.now().plusDays(10);
        Boolean isClose = false;

        Integer capacity = 30;
        Integer remainingSeats = 30;

        Long memberId = 1L;
        String memberName = "김성휘";

        LectureDto lectureDto = LectureDto.builder()
                .id(lectureId)
                .name(lectureName)
                .memberId(memberId)
                .memberName(memberName)
                .build();


        when(lectureService.create(any(LectureCreateDto.class))).thenReturn(lectureDto);

        LectureItemDto lectureItemDto = LectureItemDto.builder()
                .id(lectureItemId)
                .lectureId(lectureId)
                .capacity(capacity)
                .openDate(openDate)
                .isClose(isClose)
                .build();

        when(lectureItemService.create(any(LectureItemCreateDto.class))).thenReturn(lectureItemDto);

        LectureInventoryDto lectureInventoryDto = LectureInventoryDto.builder()
                .id(lectureInventoryId)
                .lectureId(lectureId)
                .lectureItemId(lectureItemId)
                .remainingSeats(remainingSeats)
                .build();

        when(lectureInventoryService.create(any(LectureInventoryCreateDto.class))).thenReturn(lectureInventoryDto);


        LectureInfoCreateDto lectureInfoCreateDto = LectureInfoCreateDto.builder()
                .lectureName(lectureName)
                .memberId(memberId)
                .memberName(memberName)
                .lectureId(lectureId)
                .capacity(capacity)
                .openDate(openDate)
                .isClose(isClose)
                .build();

        //when
        LectureInfoDto lectureInfo = registerFacadeService.createLectureInfo(lectureInfoCreateDto);

        //then
        assertThat(lectureInfo.getLectureId()).isEqualTo(lectureId);
        assertThat(lectureInfo.getLectureName()).isEqualTo(lectureName);
        assertThat(lectureInfo.getCapacity()).isEqualTo(capacity);
    }

}