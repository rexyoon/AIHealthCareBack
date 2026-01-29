package com.aihealthcare.aihealthcare.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class WeightSummaryResponse {
    private BigDecimal weightKg;
    private BigDecimal max;
    private BigDecimal min;
}
