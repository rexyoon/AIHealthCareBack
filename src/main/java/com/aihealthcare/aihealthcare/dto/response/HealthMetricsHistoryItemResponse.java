package com.aihealthcare.aihealthcare.dto.response;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthMetricsHistoryItemResponse {
    private Long id;
    private LocalDate date;

    private Double testosteroneTotal;
    private Double estradiolE2;
    private Double ast;
    private Double alt;

    private Integer healthScore;
}