package com.aihealthcare.aihealthcare.controller.AI;

import com.aihealthcare.aihealthcare.dto.response.HealthMetricsDashboardResponse;
import com.aihealthcare.aihealthcare.dto.response.HealthMetricsHistoryItemResponse;
import com.aihealthcare.aihealthcare.service.HealthMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/health-metrics")
public class HealthMetricsController {

    private final HealthMetricsService service;

    // GET /api/health-metrics/history?period=weekly
    @GetMapping("/history")
    public List<HealthMetricsHistoryItemResponse> history(
            @RequestParam(required = false, defaultValue = "weekly") String period,
            @RequestParam(required = false, defaultValue = "1") Long userId
            // ✅ 나중에 로그인 붙이면 userId는 여기서 빼고 principal에서 가져오면 됨
    ) {
        return service.getHistory(userId, period);
    }

    // GET /api/health-metrics/by-date?date=2026-01-20
    @GetMapping("/by-date")
    public HealthMetricsDashboardResponse byDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false, defaultValue = "1") Long userId
    ) {
        return service.getByDate(userId, date);
    }
}
