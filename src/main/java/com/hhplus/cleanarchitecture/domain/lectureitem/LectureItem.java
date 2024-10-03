package com.hhplus.cleanarchitecture.domain.lectureitem;

import com.hhplus.cleanarchitecture.domain.common.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "lecture_item")
@Entity
public class LectureItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "lecture_id")
    private Long lectureId;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "open_date")
    private LocalDate openDate;

    @Column(name = "is_close")
    private Boolean isClose;
}
