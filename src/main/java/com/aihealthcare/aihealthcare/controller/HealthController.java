package com.aihealthcare.aihealthcare.controller;

import com.aihealthcare.aihealthcare.common.ApiResponse;
import com.aihealthcare.aihealthcare.dto.request.HealthRecordCreateRequest;
import com.aihealthcare.aihealthcare.dto.response.HealthRecordResponse;
import com.aihealthcare.aihealthcare.service.HealthService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    // 서버 살아있나 체크 (가장 먼저 필요)
    @GetMapping("/ping")
    public ApiResponse<String> ping() {
        return ApiResponse.ok("pong");
    }

    // 기록 생성
    @PostMapping("/records")
    public ApiResponse<HealthRecordResponse> create(@RequestBody HealthRecordCreateRequest request) {
        return ApiResponse.ok(healthService.createRecord(request));
    }

    // 전체 조회
    @GetMapping("/records")
    public ApiResponse<List<HealthRecordResponse>> findAll() {
        return ApiResponse.ok(healthService.findAll());
    }

    // 단건 조회
    @GetMapping("/records/{id}")
    public ApiResponse<HealthRecordResponse> findOne(@PathVariable Long id) {
        return ApiResponse.ok(healthService.findOne(id));
    }

    // 삭제
    @DeleteMapping("/records/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        healthService.delete(id);
        return ApiResponse.ok(null);
    }
}
