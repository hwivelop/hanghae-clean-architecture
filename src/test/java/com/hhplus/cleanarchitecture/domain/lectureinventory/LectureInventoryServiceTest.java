package com.hhplus.cleanarchitecture.domain.lectureinventory;

import com.hhplus.cleanarchitecture.domain.lecturehistory.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.response.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LectureInventoryServiceTest {

    @Mock
    private LectureInventoryRepository lectureInventoryRepository;

    @InjectMocks
    private LectureInventoryService lectureInventoryService;


    @Test
    @DisplayName("강의의 남은 자리 정보를 등록한다.")
    void createLectureInventory() {

        //given

        final Long lectureId = 1L;
        final Long lectureItemId = 1L;
        final Integer remainingSeats = 30;

        LectureInventory lectureInventory = LectureInventory.builder()
                .lectureId(lectureId)
                .lectureItemId(lectureItemId)
                .remainingSeats(remainingSeats)
                .build();

        when(lectureInventoryRepository.save(lectureInventory)).thenReturn(lectureInventory);
//        when(lectureInventoryRepository.save(any(LectureInventory.class))).thenReturn(lectureInventory);

        LectureInventoryCreateDto dto = LectureInventoryCreateDto.builder()
                .lectureId(lectureId)
                .lectureItemId(lectureItemId)
                .remainingSeats(remainingSeats)
                .build();

        //when
        LectureInventoryDto lectureInventoryDto = lectureInventoryService.create(dto);

        //then
        assertThat(lectureInventoryDto.getLectureId()).isEqualTo(lectureId);
        assertThat(lectureInventoryDto.getLectureItemId()).isEqualTo(lectureItemId);
        assertThat(lectureInventoryDto.getRemainingSeats()).isEqualTo(remainingSeats);
    }

    @Test
    @DisplayName("잔여수가 0 초과이면 true")
    void validateSeatTrue() {

        //given
        Integer remainingSeats = 1;

        LectureInventory lectureInventory = LectureInventory.builder()
                .lectureId(1L)
                .lectureItemId(1L)
                .remainingSeats(remainingSeats)
                .build();

        //when
        Boolean validateSeat = lectureInventory.validateSeat();

        //then
        assertThat(validateSeat).isTrue();
    }

    @Test
    @DisplayName("잔여수가 0이거나, 작으면 false")
    void validateSeatFalse() {

        //given
        Integer remainingSeats = 0;

        LectureInventory lectureInventory = LectureInventory.builder()
                .lectureId(1L)
                .lectureItemId(1L)
                .remainingSeats(remainingSeats)
                .build();

        //when
        Boolean validateSeat = lectureInventory.validateSeat();

        //then
        assertThat(validateSeat).isFalse();
    }

    @Test
    @DisplayName("신청이면 좌석수를 감소시킨다.")
    void updateRemainingSeatsMinus() {

        //given
        ApplyStatus apply = ApplyStatus.APPLY;
        Integer originalRemainingSeats = 10;
        Integer resultRemainingSeats = 9;

        LectureInventory lectureInventory = LectureInventory.builder()
                .lectureId(1L)
                .lectureItemId(1L)
                .remainingSeats(originalRemainingSeats)
                .build();

        //when
        lectureInventory.updateRemainingSeats(apply);

        //then
        assertThat(lectureInventory.getRemainingSeats()).isEqualTo(resultRemainingSeats);
    }

    @Test
    @DisplayName("취소면 좌석수를 증가시킨다.")
    void updateRemainingSeatsPlus() {

        //given
        ApplyStatus apply = ApplyStatus.CANCEL;
        Integer originalRemainingSeats = 10;
        Integer resultRemainingSeats = 11;

        LectureInventory lectureInventory = LectureInventory.builder()
                .lectureId(1L)
                .lectureItemId(1L)
                .remainingSeats(originalRemainingSeats)
                .build();

        //when
        lectureInventory.updateRemainingSeats(apply);

        //then
        assertThat(lectureInventory.getRemainingSeats()).isEqualTo(resultRemainingSeats);
    }

    @Test
    @DisplayName("잔여 좌석이 충분하면 예외 발생하지 않는다.")
    void remainingSeatsEnough() {

        //given
        Integer remainingSeats = 1;

        LectureInventory lectureInventory = LectureInventory.builder()
                .lectureId(1L)
                .lectureItemId(1L)
                .remainingSeats(remainingSeats)
                .build();

        //when & then
        assertDoesNotThrow(lectureInventory::remainingSeatsZeroThenThrow);
    }

    @Test
    @DisplayName("잔여 좌석이 부족하면 예외 발생한다.")
    void remainingSeatsZeroThenThrow() {

        //given
        Integer remainingSeats = 0;

        LectureInventory lectureInventory = LectureInventory.builder()
                .lectureId(1L)
                .lectureItemId(1L)
                .remainingSeats(remainingSeats)
                .build();

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                lectureInventory::remainingSeatsZeroThenThrow);

        //then
        assertEquals("잔여 좌석이 부족합니다.", exception.getMessage());
    }
}