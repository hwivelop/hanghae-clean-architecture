package com.hhplus.cleanarchitecture.domain.lectureitem;

import com.hhplus.cleanarchitecture.domain.lecture.*;
import com.hhplus.cleanarchitecture.domain.lecture.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lecture.dto.response.*;
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
class LectureItemServiceTest {

    @Mock
    private LectureItemRepository lectureItemRepository;

    @InjectMocks
    private LectureItemService lectureItemService;


    @Test
    @DisplayName("강의의 상세 정보를 등록한다.")
    void createLectureItem() {

        //given

        final Long lectureId = 1L;
        final Integer capacity = 30;
        final LocalDate openDate = LocalDate.now().plusDays(10);
        final Boolean isClose = false;

        LectureItem lectureItem = LectureItem.builder()
                .lectureId(lectureId)
                .capacity(capacity)
                .openDate(openDate)
                .isClose(isClose)
                .build();

        when(lectureItemRepository.save(lectureItem)).thenReturn(lectureItem);
//        when(lectureItemRepository.save(any(LectureItem.class))).thenReturn(lectureItem);

        LectureItemCreateDto dto = LectureItemCreateDto.builder()
                .lectureId(lectureId)
                .capacity(capacity)
                .openDate(openDate)
                .isClose(isClose)
                .build();

        //when
        LectureItemDto lectureItemDto = lectureItemService.create(dto);

        //then
        assertThat(lectureItemDto.getLectureId()).isEqualTo(lectureId);
        assertThat(lectureItemDto.getCapacity()).isEqualTo(capacity);
        assertThat(lectureItemDto.getIsClose()).isEqualTo(isClose);
    }

}