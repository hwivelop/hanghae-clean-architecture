package com.hhplus.cleanarchitecture.domain.lecturehistory;

import com.hhplus.cleanarchitecture.domain.common.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "lecture_history")
@Entity
public class LectureHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "lecture_id")
    private Long lectureId;

    @Column(name = "lecture_item_id")
    private Long lectureItemId;

    @Column(name = "apply_status")
    private ApplyStatus applyStatus;
}
