package com.aihealthcare.aihealthcare.controller;

import com.aihealthcare.aihealthcare.dto.request.HealthRecordRequest;
import com.aihealthcare.aihealthcare.dto.response.HealthRecordResponse;
import com.aihealthcare.aihealthcare.service.HealthRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // React 연동 허용
public class HealthRecordController {

    private final HealthRecordService healthRecordService;

    // 1. 기록 저장: POST /api/health
    @PostMapping
    public ResponseEntity<Long> saveHealthRecord(@RequestBody HealthRecordRequest request) {
        Long recordId = healthRecordService.createRecord(request);
        return ResponseEntity.ok(recordId);
    }

    // 2. 내 기록 전체 조회: GET /api/health/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HealthRecordResponse>> getMyRecords(@PathVariable Long userId) {
        return ResponseEntity.ok(healthRecordService.getRecordsByUserId(userId));
    }

    // 3. 기록 수정: PUT /api/health/{recordId}
    @PutMapping("/{recordId}")
    public ResponseEntity<Void> updateHealthRecord(@PathVariable Long recordId, @RequestBody HealthRecordRequest request) {
        healthRecordService.updateRecord(recordId, request);
        return ResponseEntity.ok().build();
    }

    // 4. 기록 삭제: DELETE /api/health/{recordId}
    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteHealthRecord(@PathVariable Long recordId) {
        healthRecordService.deleteRecord(recordId);
        return ResponseEntity.ok().build();
    }
}
