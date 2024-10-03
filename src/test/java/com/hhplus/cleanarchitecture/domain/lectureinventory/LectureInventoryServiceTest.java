package com.hhplus.cleanarchitecture.domain.lectureinventory;

import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.response.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.assertj.core.api.Assertions.*;
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

}