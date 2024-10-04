package com.hhplus.cleanarchitecture.domain.lectureitem;

import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface LectureItemRepository extends JpaRepository<LectureItem, Long> {

    List<LectureItem> findAllByIsClose(boolean isClosed);
}
