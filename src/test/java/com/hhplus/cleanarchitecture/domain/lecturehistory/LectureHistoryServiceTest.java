package com.hhplus.cleanarchitecture.domain.lecturehistory;

import com.hhplus.cleanarchitecture.domain.lecturehistory.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lecturehistory.dto.response.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.response.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LectureHistoryServiceTest {

    @Mock
    private LectureHistoryRepository lectureHistoryRepository;

    @InjectMocks
    private LectureHistoryService lectureHistoryService;


    @Test
    @DisplayName("강의 등록 히스토리를 등록한다.")
    void createLectureInventory() {

        //given

        final Long memberId = 1L;
        final Long lectureId = 1L;

        LectureHistory lectureHistory = LectureHistory.builder()
                .memberId(memberId)
                .lectureId(lectureId)
                .build();

        when(lectureHistoryRepository.save(lectureHistory)).thenReturn(lectureHistory);
//        when(lectureHistoryRepository.save(any(LectureHistory.class))).thenReturn(lectureHistory);

        LectureHistoryCreateDto dto = LectureHistoryCreateDto.builder()
                .memberId(memberId)
                .lectureId(lectureId)
                .build();

        //when
        LectureHistoryDto lectureHistoryDto = lectureHistoryService.create(dto);

        //then
        assertThat(lectureHistoryDto.getMemberId()).isEqualTo(memberId);
        assertThat(lectureHistoryDto.getLectureId()).isEqualTo(lectureId);
    }
}