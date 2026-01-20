package com.aihealthcare.aihealthcare.repository.Health;

import com.aihealthcare.aihealthcare.domain.health.HealthMetrics;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HealthMetricsRepository extends JpaRepository<HealthMetrics, Long> {

    List<HealthMetrics> findByUserIdOrderByDateDesc(Long userId);

    List<HealthMetrics> findByUserIdOrderByDateDesc(Long userId, Pageable pageable);

    Optional<HealthMetrics> findByUserIdAndDate(Long userId, LocalDate date);
}
