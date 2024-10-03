package com.hhplus.cleanarchitecture.domain.lectureinventory;

import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface LectureInventoryRepository extends JpaRepository<LectureInventory, Long> {

    Optional<LectureInventory> findByLectureIdAndLectureItemId(Long lectureId, Long lectureItemId);
}
