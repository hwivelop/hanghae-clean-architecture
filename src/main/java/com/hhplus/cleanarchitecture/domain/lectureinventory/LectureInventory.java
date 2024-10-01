package com.hhplus.cleanarchitecture.domain.lectureinventory;

import com.hhplus.cleanarchitecture.domain.common.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Getter
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
}
