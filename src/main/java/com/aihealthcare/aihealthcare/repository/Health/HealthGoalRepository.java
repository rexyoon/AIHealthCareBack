package com.aihealthcare.aihealthcare.repository.Health;

import com.aihealthcare.aihealthcare.domain.health.HealthGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthGoalRepository extends JpaRepository<HealthGoal, Long> {
    Optional<HealthGoal>findTopByUserIdOrderByStartDateDesc(Long userId);
}