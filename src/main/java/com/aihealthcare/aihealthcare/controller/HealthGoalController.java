package com.aihealthcare.aihealthcare.controller;

import com.aihealthcare.aihealthcare.domain.HealthGoal;
import com.aihealthcare.aihealthcare.domain.HealthGoal;
import com.aihealthcare.aihealthcare.domain.user.User;
import com.aihealthcare.aihealthcare.repository.HealthGoalRepository;
import com.aihealthcare.aihealthcare.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HealthGoalController {

    private final HealthGoalRepository healthGoalRepository;
    private final UserRepository userRepository;

    // 1. 목표 설정 (생성)
    @PostMapping
    @Transactional
    public ResponseEntity<Long> setGoal(@RequestBody GoalRequest request) {
        User user = userRepository.findById(request.userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        HealthGoal goal = HealthGoal.builder()
                .user(user)
                .targetWeight(request.targetWeight)
                .targetBloodSugar(request.targetBloodSugar)
                .endDate(request.endDate)
                .build();

        return ResponseEntity.ok(healthGoalRepository.save(goal).getId());
    }

    // 2. 현재 목표 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<HealthGoal> getCurrentGoal(@PathVariable Long userId) {
        return ResponseEntity.of(healthGoalRepository.findTopByUserIdOrderByStartDateDesc(userId));
    }

    @Data
    public static class GoalRequest {
        private Long userId;
        private Double targetWeight;
        private Double targetBloodSugar;
        private LocalDate endDate;
    }
}
