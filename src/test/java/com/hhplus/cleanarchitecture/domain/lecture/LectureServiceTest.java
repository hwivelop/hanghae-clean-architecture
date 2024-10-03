package com.hhplus.cleanarchitecture.domain.lecture;

import com.hhplus.cleanarchitecture.domain.lecture.dto.request.*;
import com.hhplus.cleanarchitecture.domain.lecture.dto.response.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;


    @Test
    @DisplayName("강의를 등록한다.")
    void createLecture() {

        //given

        final String lectureName = "클린아키텍쳐";
        final Long memberId = 1L;
        final String memberName = "김성휘";

        Lecture lecture = Lecture.builder()
                .name(lectureName)
                .memberId(memberId)
                .memberName(memberName)
                .build();

        when(lectureRepository.save(lecture)).thenReturn(lecture);
//        when(lectureRepository.save(any(Lecture.class))).thenReturn(lecture);

        LectureCreateDto dto = LectureCreateDto.builder()
                .name(lectureName)
                .memberId(memberId)
                .MemberName(memberName)
                .build();

        //when
        LectureDto lectureDto = lectureService.create(dto);

        //then
        assertThat(lectureDto.getName()).isEqualTo(lectureName);
        assertThat(lectureDto.getMemberName()).isEqualTo(memberName);
    }
}