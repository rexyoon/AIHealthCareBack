package com.aihealthcare.aihealthcare.controller.AI;

import com.aihealthcare.aihealthcare.dto.request.WeightLogCreateRequest;
import com.aihealthcare.aihealthcare.dto.response.WeightLogPointResponse;
import com.aihealthcare.aihealthcare.dto.response.WeightSummaryResponse;
import com.aihealthcare.aihealthcare.service.WeightLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weight")
public class WeightLogController {

    private final WeightLogService weightLogService;

    // ✅ 체중 추가 (health_log에 한 줄 insert)
    @PostMapping
    public void add(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody WeightLogCreateRequest req
    ) {
        weightLogService.addWeight(userId, req);
    }

    // ✅ 그래프용: 날짜별 포인트 리스트
    @GetMapping
    public List<WeightLogPointResponse> list(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return weightLogService.listDaily(userId, from, to);
    }

    // ✅ 최근/최고/최저
    @GetMapping("/summary")
    public WeightSummaryResponse summary(
            @RequestHeader("X-USER-ID") Long userId
    ) {
        return weightLogService.summary(userId);
    }
}
