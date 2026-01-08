package com.aihealthcare.aihealthcare.repository;


import com.aihealthcare.aihealthcare.domain.Blood.BloodMetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BloodMetricRepository extends JpaRepository<BloodMetricEntity, Long> {
    // 특정 검사 결과(testId)에 해당하는 수치 데이터를 가져옴
    Optional<BloodMetricEntity> findByBloodTest_TestId(Long testId);
}
