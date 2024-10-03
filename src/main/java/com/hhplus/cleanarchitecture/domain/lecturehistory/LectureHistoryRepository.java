package com.hhplus.cleanarchitecture.domain.lecturehistory;

import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface LectureHistoryRepository extends JpaRepository<LectureHistory, Long> {

    List<LectureHistory> findByMemberId(long memberId);
}
