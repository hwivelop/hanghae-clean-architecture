package com.hhplus.cleanarchitecture.domain.lectureinventory;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface LectureInventoryRepository extends JpaRepository<LectureInventory, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LectureInventory> findByLectureIdAndLectureItemId(Long lectureId, Long lectureItemId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT li FROM LectureInventory li WHERE li.lectureId = :lectureId AND li.lectureItemId = :lectureItemId")
    Optional<LectureInventory> findByLectureIdAndLectureItemIdWithLock(@Param("lectureId") Long lectureId, @Param("lectureItemId") Long lectureItemId);
}