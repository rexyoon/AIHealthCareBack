package com.aihealthcare.aihealthcare.controller.AI;

import com.aihealthcare.aihealthcare.service.AiCoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/coach")
@RequiredArgsConstructor
public class AiCoachController {

    private final AiCoachService aiCoachService;

    /**
     * 특정 혈액 검사(testId)에 대한 AI 코치 분석 요청
     * URL 예시: GET /api/coach/analyze/1
     */
    @GetMapping("/analyze/{testId}")
    public ResponseEntity<Map<String, String>> analyzeBloodTest(@PathVariable Long testId) {

        try {
            // 1. 서비스 호출 (DB 조회 -> 프롬프트 생성 -> AI 분석)
            String analysisResult = aiCoachService.analyzeBloodTest(testId);

            // 2. 결과 반환 (JSON 형식: {"response": "분석 내용..."})
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "response", analysisResult
            ));

        } catch (RuntimeException e) {
            // DB에 데이터가 없거나 오류 발생 시 처리
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }
}
