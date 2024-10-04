
package com.hhplus.cleanarchitecture.domain.facade;

import com.hhplus.cleanarchitecture.domain.facade.dto.request.*;
import com.hhplus.cleanarchitecture.domain.facade.dto.response.*;
import com.hhplus.cleanarchitecture.domain.lecturehistory.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.*;
import com.hhplus.cleanarchitecture.domain.lectureinventory.dto.response.*;
import com.hhplus.cleanarchitecture.domain.member.*;
import com.hhplus.cleanarchitecture.domain.member.dto.request.*;
import com.hhplus.cleanarchitecture.domain.member.dto.response.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegisterFacadeIntegrationServiceTest {

    @Autowired
    private RegisterFacadeService registerFacadeService;

    @Autowired
    private LectureInventoryService lectureInventoryService;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("잔여 좌석까지만 정상 신청된다.")
    @Transactional
    void remainingEnough() {

        // given
        int threadCount = 30;

        // 멤버 생성 로직 추가
        for (int i = 1; i <= threadCount; i++) {
            String memberName = "member" + i;
            MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                    .name(memberName)
                    .memberType(MemberType.STUDENT)
                    .build();
            memberService.create(memberCreateDto);
        }

        // when
        List<MemberDto> memberAll = memberService.getByAll();

        //then
        assertThat(memberAll.size()).isEqualTo(threadCount);

        //given
        long teacherId = 1L;
        String teacherName = "mangKyu";
        long lectureId = 1L;
        long lectureItemId = 1L;
        long inventoryId = 1L;
        String lectureName = "클린아키텍쳐";
        int capacity = 30;
        LocalDate openDate = LocalDate.now().plusDays(30);
        boolean isClose = false;

        // 초기 잔여 좌석을 30으로 설정
        LectureInfoCreateDto lectureInfoCreateDto = LectureInfoCreateDto.builder()
                .lectureName(lectureName)
                .memberId(teacherId)
                .memberName(teacherName)
                .lectureId(lectureId)
                .capacity(capacity)
                .openDate(openDate)
                .isClose(isClose)
                .build();

        LectureInfoDto lectureInfo = registerFacadeService.createLectureInfo(lectureInfoCreateDto);
        assertThat(lectureInfo.getLectureName()).isEqualTo(lectureName);
        assertThat(lectureInfo.getCapacity()).isEqualTo(capacity);
        assertThat(lectureInfo.getLectureInventoryId()).isEqualTo(inventoryId);

        // when
        for (int i = 1; i <= threadCount; i++) {

            long memberId = i;

            System.out.println("memberId " + memberId + " - 수강신청 시작");

            LectureApplyDto applyDto = LectureApplyDto.builder()
                    .memberId(memberId)
                    .lectureId(lectureId)
                    .lectureItemId(lectureItemId)
                    .applyStatus(ApplyStatus.APPLY)
                    .build();
            try {
                registerFacadeService.applyLecture(applyDto);
                List<LectureApplyHistoryDto> history = registerFacadeService.getApplyHistoryByMemberId(memberId);

                assertThat(history.size()).isEqualTo(1);
                assertThat(history.get(0).getLectureId()).isEqualTo(lectureId);
                assertThat(history.get(0).getLectureItemId()).isEqualTo(lectureItemId);

            } catch (Exception ignored) {
                System.out.println("수강 신청 시 예외 발생 = " + ignored.getMessage());
            }
        }

        // then
        LectureInventoryDto updatedInventory = lectureInventoryService.getOrThrow(lectureId, lectureItemId);
        assertThat(updatedInventory.getRemainingSeats()).isEqualTo(0); // 잔여 좌석이 0이어야 함
    }

    @Test
    @DisplayName("정원 초과시 예외발생한다.")
    @Transactional
    void remainingOverThenThrow() {

        // given
        int threadCount = 1;

        // 멤버 생성 로직 추가
        for (int i = 1; i <= threadCount; i++) {
            String memberName = "member" + i;
            MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                    .name(memberName)
                    .memberType(MemberType.STUDENT)
                    .build();
            memberService.create(memberCreateDto);
        }

        // when
        List<MemberDto> memberAll = memberService.getByAll();

        //then
        assertThat(memberAll.size()).isEqualTo(threadCount);

        //given
        long teacherId = 1L;
        String teacherName = "mangKyu";
        long lectureId = 1L;
        long lectureItemId = 1L;
        long inventoryId = 1L;
        String lectureName = "클린아키텍쳐";
        int capacity = 0;
        LocalDate openDate = LocalDate.now().plusDays(30);
        boolean isClose = false;

        LectureInfoCreateDto lectureInfoCreateDto = LectureInfoCreateDto.builder()
                .lectureName(lectureName)
                .memberId(teacherId)
                .memberName(teacherName)
                .lectureId(lectureId)
                .capacity(capacity)
                .openDate(openDate)
                .isClose(isClose)
                .build();

        LectureInfoDto lectureInfo = registerFacadeService.createLectureInfo(lectureInfoCreateDto);
        assertThat(lectureInfo.getLectureName()).isEqualTo(lectureName);
        assertThat(lectureInfo.getCapacity()).isEqualTo(capacity);
        assertThat(lectureInfo.getLectureInventoryId()).isEqualTo(inventoryId);

        // when

        Long memberId = 1L;
        LectureApplyDto applyDto = LectureApplyDto.builder()
                .memberId(memberId)
                .lectureId(lectureId)
                .lectureItemId(lectureItemId)
                .applyStatus(ApplyStatus.APPLY)
                .build();

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                registerFacadeService.applyLecture(applyDto)
        );

        //then
        assertEquals("잔여 좌석이 부족합니다.", exception.getMessage());

    }

    @Test
    @DisplayName("스레드를 돌리면, jpa lock을 건 repository 메서드가 정상 동작하지 않음")
    @Transactional
    void codeReview() throws InterruptedException {

        // given
        long teacherId = 1L;
        String teacherName = "mangKyu";
        long lectureId = 1L;
        long lectureItemId = 1L;
        String lectureName = "클린아키텍쳐";
        int capacity = 30;
        LocalDate openDate = LocalDate.now().plusDays(30);
        boolean isClose = false;
        int threadCount = 40;

        // 멤버 생성 로직 추가
        for (int i = 1; i <= threadCount; i++) {
            String memberName = "member" + i;
            MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                    .name(memberName)
                    .memberType(MemberType.STUDENT)
                    .build();
            memberService.create(memberCreateDto);
        }

        List<MemberDto> memberAll = memberService.getByAll();
        assertThat(memberAll.size()).isEqualTo(threadCount);

        // 초기 잔여 좌석을 30으로 설정
        LectureInfoCreateDto lectureInfoCreateDto = LectureInfoCreateDto.builder()
                .lectureName(lectureName)
                .memberId(teacherId)
                .memberName(teacherName)
                .lectureId(lectureId)
                .capacity(capacity)
                .openDate(openDate)
                .isClose(isClose)
                .build();

        LectureInfoDto lectureInfo = registerFacadeService.createLectureInfo(lectureInfoCreateDto);
        assertThat(lectureInfo.getLectureName()).isEqualTo(lectureName);
        assertThat(lectureInfo.getCapacity()).isEqualTo(capacity);

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 1; i <= threadCount; i++) {

            long memberId = i;

            executorService.submit(() -> {

                System.out.println("memberId " + memberId + " - 수강신청 시작");

                LectureApplyDto applyDto = LectureApplyDto.builder()
                        .memberId(memberId)
                        .lectureId(lectureId)
                        .lectureItemId(lectureItemId)
                        .applyStatus(ApplyStatus.APPLY)
                        .build();
                try {

                    /**
                     * code review 쓰기 락을 적용하니까 lectureInventory 객체를 조회할 수 없어 null이 반환되는 이슈가 발생
                     */
                    registerFacadeService.applyLecture(applyDto);


                    List<LectureApplyHistoryDto> history = registerFacadeService.getApplyHistoryByMemberId(memberId);

                    assertThat(history.size()).isEqualTo(1);
                    assertThat(history.get(0).getMemberId()).isEqualTo(memberId);
                    assertThat(history.get(0).getLectureId()).isEqualTo(lectureId);
                    assertThat(history.get(0).getLectureItemId()).isEqualTo(lectureItemId);

                } catch (Exception ignored) {
                    System.out.println("수강 신청 시 예외 발생 = " + ignored.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드가 작업을 마칠 때까지 대기
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        // then
        LectureInventoryDto updatedInventory = lectureInventoryService.getOrThrow(lectureId, lectureItemId);
        assertThat(updatedInventory.getRemainingSeats()).isEqualTo(0); // 잔여 좌석이 0이어야 함
    }
}