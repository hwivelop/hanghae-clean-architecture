package com.hhplus.cleanarchitecture.domain.lecture;

import com.hhplus.cleanarchitecture.domain.common.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "lecture")
@Entity
public class Lecture extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_name")
    private String memberName;
}
