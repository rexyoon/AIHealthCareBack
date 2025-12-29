package com.aihealthcare.aihealthcare.repository;

import com.aihealthcare.aihealthcare.domain.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
    // 특정 유저의 기록을 최신순으로 가져오기
    List<HealthRecord> findAllByUserIdOrderByRecordedAtDesc(Long userId);
}
