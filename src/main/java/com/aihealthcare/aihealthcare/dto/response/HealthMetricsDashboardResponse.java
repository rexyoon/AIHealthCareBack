package com.aihealthcare.aihealthcare.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthMetricsDashboardResponse {

    private Long id;
    private LocalDate date;

    private Double testosteroneTotal;
    private Double testosteroneFree;
    private Double estradiolE2;

    private Double ast;
    private Double alt;

    private Integer healthScore;
}
