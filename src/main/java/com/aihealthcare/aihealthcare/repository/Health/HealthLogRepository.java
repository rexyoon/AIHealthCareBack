package com.aihealthcare.aihealthcare.repository.Health;

import com.aihealthcare.aihealthcare.domain.health.HealthLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HealthLogRepository extends JpaRepository<HealthLogEntity, Long> {

    // ✅ 체중 기록만 가져오고 싶으면 weightKg가 null 아닌 것만
    List<HealthLogEntity> findAllByUser_UserIdAndWeightKgIsNotNullOrderByRecordedAtAsc(Long userId);

    List<HealthLogEntity> findAllByUser_UserIdAndWeightKgIsNotNullAndRecordedAtBetweenOrderByRecordedAtAsc(
            Long userId, LocalDateTime from, LocalDateTime to
    );
}
