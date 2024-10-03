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
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterFacadeServiceTest {

    @Mock
    private LectureService lectureService;

    @Mock
    private LectureInventoryService lectureInventoryService;

    @Mock
    private LectureItemService lectureItemService;

    @Mock
    private LectureHistoryService lectureHistoryService;

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

    @Test
    @DisplayName("신청 가능한 강의 목록이 날짜별로 그룹화되어 반환된다.")
    void getAvailableLectureList_groupedByDate() {

        //given
        LocalDate date1 = LocalDate.now().plusDays(1);
        LocalDate date2 = LocalDate.now().plusDays(2);

        LectureItemDto lectureItemDto1 = LectureItemDto.builder()
                .lectureId(1L)
                .openDate(date1)
                .build();

        LectureItemDto lectureItemDto2 = LectureItemDto.builder()
                .lectureId(2L)
                .openDate(date2)
                .build();

        LectureDto lectureDto1 = LectureDto.builder()
                .id(1L)
                .name("클린아키텍쳐1")
                .build();

        LectureDto lectureDto2 = LectureDto.builder()
                .id(2L)
                .name("클린아키텍쳐2")
                .build();

        when(lectureItemService.getAvailable()).thenReturn(List.of(lectureItemDto1, lectureItemDto2));
        when(lectureService.getOrThrow(1L)).thenReturn(lectureDto1);
        when(lectureService.getOrThrow(2L)).thenReturn(lectureDto2);

        //when
        Map<LocalDate, List<LectureInfoDto>> result = registerFacadeService.getAvailableLectureList();

        //then
        assertThat(result).hasSize(2);
        assertThat(result.get(date1)).hasSize(1);
        assertThat(result.get(date2)).hasSize(1);

        LectureInfoDto lectureInfo1 = result.get(date1).get(0);
        LectureInfoDto lectureInfo2 = result.get(date2).get(0);

        assertThat(lectureInfo1.getLectureId()).isEqualTo(1L);
        assertThat(lectureInfo1.getLectureName()).isEqualTo("클린아키텍쳐1");

        assertThat(lectureInfo2.getLectureId()).isEqualTo(2L);
        assertThat(lectureInfo2.getLectureName()).isEqualTo("클린아키텍쳐2");
    }

    @Test
    @DisplayName("신규 신청한 강의는 예외가 발생하지 않는다.")
    void newApplyLecture() {

        //given
        long memberId = 1L;
        long lectureId = 1L;
        long lectureItemId = 1L;

        LectureApplyDto lectureApplyDto = LectureApplyDto.builder()
                .memberId(memberId)
                .applyStatus(ApplyStatus.APPLY)
                .lectureId(lectureId)
                .lectureItemId(lectureItemId)
                .build();

        LectureInventoryDto lectureInventoryDto = LectureInventoryDto.builder()
                .lectureId(lectureId)
                .lectureItemId(lectureItemId)
                .remainingSeats(5)
                .build();

        doNothing().when(lectureHistoryService).ifApplyHistoryExistThenThrow(memberId, lectureId, lectureItemId);
        when(lectureInventoryService.updateLectureInventoryInfo(lectureApplyDto)).thenReturn(lectureInventoryDto);

        //when
        registerFacadeService.applyLecture(lectureApplyDto);

        //then
        verify(lectureHistoryService).ifApplyHistoryExistThenThrow(memberId, lectureId, lectureItemId);
        verify(lectureInventoryService).updateLectureInventoryInfo(lectureApplyDto);
        verify(lectureHistoryService).create(any(LectureHistoryCreateDto.class));
    }

    @Test
    @DisplayName("동일한 사용자가 동일한 특강을 중복 신청하면 예외 발생한다.")
    void duplicateApplyThenThrow() {

        //given
        long memberId = 1L;
        long lectureId = 1L;
        long lectureItemId = 1L;

        doThrow(new IllegalArgumentException("이미 신청한 강의입니다."))
                .when(lectureHistoryService).ifApplyHistoryExistThenThrow(memberId, lectureId, lectureItemId);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                lectureHistoryService.ifApplyHistoryExistThenThrow(memberId, lectureId, lectureItemId)
        );

        //then
        assertEquals("이미 신청한 강의입니다.", exception.getMessage());
    }
}