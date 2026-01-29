package com.aihealthcare.aihealthcare.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class WeightLogCreateRequest {
    private BigDecimal weightKg;         // 필수
    private LocalDateTime recordedAt;    // 선택(없으면 now)
}