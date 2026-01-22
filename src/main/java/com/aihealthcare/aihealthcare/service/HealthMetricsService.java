package com.aihealthcare.aihealthcare.service;

import com.aihealthcare.aihealthcare.domain.health.HealthMetrics;
import com.aihealthcare.aihealthcare.dto.response.HealthMetricsDashboardResponse;
import com.aihealthcare.aihealthcare.dto.response.HealthMetricsHistoryItemResponse;
import com.aihealthcare.aihealthcare.repository.Health.HealthMetricsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HealthMetricsService {

    private final HealthMetricsRepository repository;

    public List<HealthMetricsHistoryItemResponse> getHistory(Long userId, String period) {
        int limit = resolveLimit(period);

        List<HealthMetrics> list = repository.findByUserIdOrderByMetricsDateDesc(
                userId,
                PageRequest.of(0, limit)
        );

        return list.stream().map(this::toHistoryItem).toList();
    }

    public HealthMetricsDashboardResponse getByDate(Long userId, LocalDate date) {
        HealthMetrics m = repository.findByUserIdAndMetricsDate(userId, date)
                .orElseThrow(() -> new IllegalArgumentException("No metrics for date: " + date));

        return toDashboard(m);
    }

    private int resolveLimit(String period) {
        if (period == null) return 7;
        return switch (period.toLowerCase()) {
            case "weekly" -> 7;
            case "monthly" -> 30;
            default -> 7;
        };
    }

    private HealthMetricsHistoryItemResponse toHistoryItem(HealthMetrics m) {
        return HealthMetricsHistoryItemResponse.builder()
                .date(m.getMetricsDate())
                .testosteroneTotal(m.getTestosteroneTotal())
                .estradiolE2(m.getEstradiolE2())
                .ast(m.getAst())
                .alt(m.getAlt())
                .healthScore(m.getHealthScore())
                .build();
    }

    private HealthMetricsDashboardResponse toDashboard(HealthMetrics m) {
        return HealthMetricsDashboardResponse.builder()
                .date(m.getMetricsDate())
                .testosteroneTotal(m.getTestosteroneTotal())
                .testosteroneFree(m.getTestosteroneFree())
                .estradiolE2(m.getEstradiolE2())
                .ast(m.getAst())
                .alt(m.getAlt())
                .healthScore(m.getHealthScore())
                .build();
    }
}
