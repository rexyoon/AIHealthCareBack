package com.aihealthcare.aihealthcare.controller;

import com.aihealthcare.aihealthcare.domain.AI.AiAnalysisLog;
import com.aihealthcare.aihealthcare.repository.AiAnalysisLogResporitory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai-logs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AiLogController {
    private final AiAnalysisLogResporitory aiAnalysisLogResporitory;
    @GetMapping("user/{userId}")
    public ResponseEntity<List<AiAnalysisLog>> getLogs(@PathVariable Long userId) {
        return ResponseEntity.ok(aiAnalysisLogResporitory.findAllByUserIdOrderByCreatedAtDesc(userId));
    }
}