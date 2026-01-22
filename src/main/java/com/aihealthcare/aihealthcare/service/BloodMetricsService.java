package com.aihealthcare.aihealthcare.service;

import com.aihealthcare.aihealthcare.domain.Blood.BloodMetricEntity;
import com.aihealthcare.aihealthcare.repository.BloodMetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BloodMetricsService {

    private final BloodMetricRepository repository;

    // 특정 testId에 대한 혈액 지표를 가져오는 메서드
    public Optional<BloodMetricEntity> getMetricsByTestId(Long testId) {
        return repository.findByBloodTest_TestId(testId);
    }

    // 모든 혈액 지표를 가져오는 메서드
    public List<BloodMetricEntity> getAllMetrics() {
        return repository.findAll(); // 모든 혈액 지표를 반환
    }
}
