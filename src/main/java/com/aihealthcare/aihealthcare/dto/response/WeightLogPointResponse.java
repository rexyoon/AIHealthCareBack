package com.aihealthcare.aihealthcare.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class WeightLogPointResponse {
    private LocalDate date;        // 그래프 x축(날짜)
    private BigDecimal weightKg;   // y축(체중)
}