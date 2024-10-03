package com.hhplus.cleanarchitecture.domain.lectureinventory;

import com.hhplus.cleanarchitecture.domain.common.*;
import com.hhplus.cleanarchitecture.domain.lecturehistory.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "lecture_inventory")
@Entity
public class LectureInventory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "lecture_id")
    private Long lectureId;

    @Column(name = "lecture_item_id")
    private Long lectureItemId;

    @Column(name = "remaining_seats")
    private Integer remainingSeats;

    /**
     * 현재 잔여 좌석 수가 0보다 큰지 확인
     */
    public Boolean validateSeat() {
        return this.remainingSeats > 0;
    }

    /**
     * 좌석수 업데이트
     */
    public void updateRemainingSeats(final ApplyStatus status) {
        if (status.equals(ApplyStatus.APPLY)) {
            this.remainingSeats -= 1;  // 좌석을 감소시킴
        } else {
            this.remainingSeats += 1;  // 취소 시 좌석을 증가시킴
        }
    }

    /**
     * 잔여 좌석이 부족하면 예외 발생
     */
    public void remainingSeatsZeroThenThrow() {

        if (!validateSeat()) {
            throw new IllegalArgumentException("잔여 좌석이 부족합니다.");
        }
    }
}