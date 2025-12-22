package com.aihealthcare.aihealthcare.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthRecordUpdateRequest {
    private BigDecimal weightKg;
    private String note;
}
