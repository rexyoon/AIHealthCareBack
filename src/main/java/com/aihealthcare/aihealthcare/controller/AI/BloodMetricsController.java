package com.aihealthcare.aihealthcare.controller.AI;

import com.aihealthcare.aihealthcare.domain.Blood.BloodMetricEntity;
import com.aihealthcare.aihealthcare.service.BloodMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blood-metrics")
@RequiredArgsConstructor
public class BloodMetricsController {

    private final BloodMetricsService service;

    // 특정 testId에 대한 혈액 지표를 반환하는 API
    @GetMapping("/{testId}")
    public ResponseEntity<BloodMetricEntity> getMetrics(@PathVariable Long testId) {
        Optional<BloodMetricEntity> metrics = service.getMetricsByTestId(testId);

        return metrics.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 모든 혈액 지표를 가져오는 API
    @GetMapping
    public ResponseEntity<List<BloodMetricEntity>> getAllMetrics() {
        List<BloodMetricEntity> metrics = service.getAllMetrics();
        return ResponseEntity.ok(metrics);
    }
}
