package com.aihealthcare.aihealthcare.service;

import com.aihealthcare.aihealthcare.domain.health.HealthLogEntity;
import com.aihealthcare.aihealthcare.domain.user.UserEntity;
import com.aihealthcare.aihealthcare.dto.request.WeightLogCreateRequest;
import com.aihealthcare.aihealthcare.dto.response.WeightLogPointResponse;
import com.aihealthcare.aihealthcare.dto.response.WeightSummaryResponse;
import com.aihealthcare.aihealthcare.exception.ApiException;
import com.aihealthcare.aihealthcare.repository.Health.HealthLogRepository;
import com.aihealthcare.aihealthcare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WeightLogService {

    private final HealthLogRepository healthLogRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addWeight(Long userId, WeightLogCreateRequest req) {
        if (req.getWeightKg() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "weightKg는 필수입니다.");
        }

        BigDecimal w = req.getWeightKg();
        if (w.compareTo(new BigDecimal("20")) < 0 || w.compareTo(new BigDecimal("300")) > 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "몸무게 범위가 올바르지 않습니다. (20~300kg)");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "유효하지 않은 사용자입니다."));

        HealthLogEntity log = HealthLogEntity.builder()
                .user(user)
                .recordedAt(req.getRecordedAt() == null ? LocalDateTime.now() : req.getRecordedAt())
                .weightKg(w)
                // ✅ 나머지는 null 그대로 (안 쓰는 컬럼)
                .build();

        healthLogRepository.save(log);
    }

    /**
     * 그래프용: 날짜별 1개 포인트로 내려줌
     * 같은 날짜에 여러 기록이 있으면 "마지막 기록"을 사용 (실사용에서 자연스러움)
     */
    @Transactional(readOnly = true)
    public List<WeightLogPointResponse> listDaily(Long userId, LocalDate from, LocalDate to) {
        List<HealthLogEntity> rows;

        if (from != null && to != null) {
            LocalDateTime start = from.atStartOfDay();
            LocalDateTime end = to.plusDays(1).atStartOfDay().minusNanos(1);
            rows = healthLogRepository.findAllByUser_UserIdAndWeightKgIsNotNullAndRecordedAtBetweenOrderByRecordedAtAsc(
                    userId, start, end
            );
        } else {
            rows = healthLogRepository.findAllByUser_UserIdAndWeightKgIsNotNullOrderByRecordedAtAsc(userId);
        }

        // 날짜별 마지막 기록으로 압축
        Map<LocalDate, HealthLogEntity> lastByDate = new LinkedHashMap<>();
        for (HealthLogEntity r : rows) {
            LocalDate d = r.getRecordedAt().toLocalDate();
            lastByDate.put(d, r); // 같은 날짜면 뒤에 온 게 덮어써짐 = 마지막 기록
        }

        List<WeightLogPointResponse> result = new ArrayList<>();
        for (Map.Entry<LocalDate, HealthLogEntity> e : lastByDate.entrySet()) {
            result.add(new WeightLogPointResponse(e.getKey(), e.getValue().getWeightKg()));
        }

        // 날짜 오름차순 보장
        result.sort(Comparator.comparing(WeightLogPointResponse::getDate));
        return result;
    }

    @Transactional(readOnly = true)
    public WeightSummaryResponse summary(Long userId) {
        List<WeightLogPointResponse> points = listDaily(userId, null, null);
        if (points.isEmpty()) return new WeightSummaryResponse(null, null, null);

        BigDecimal latest = points.get(points.size() - 1).getWeightKg();
        BigDecimal max = points.get(0).getWeightKg();
        BigDecimal min = points.get(0).getWeightKg();

        for (WeightLogPointResponse p : points) {
            if (p.getWeightKg().compareTo(max) > 0) max = p.getWeightKg();
            if (p.getWeightKg().compareTo(min) < 0) min = p.getWeightKg();
        }

        return new WeightSummaryResponse(latest, max, min);
    }
}
