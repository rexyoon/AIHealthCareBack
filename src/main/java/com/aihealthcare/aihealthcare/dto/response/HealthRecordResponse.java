package com.aihealthcare.aihealthcare.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthRecordResponse {
    private Long id;
    private LocalDate recordDate;
    private BigDecimal weightKg;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
