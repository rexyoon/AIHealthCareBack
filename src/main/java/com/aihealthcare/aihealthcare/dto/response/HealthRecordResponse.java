package com.aihealthcare.aihealthcare.dto.response;

import com.aihealthcare.aihealthcare.domain.HealthRecordEntity;

import java.time.LocalDateTime;

public class HealthRecordResponse {

    private Long id;
    private Double weightKg;
    private String memo;
    private LocalDateTime recordedAt;

    public static HealthRecordResponse from(HealthRecordEntity entity) {
        HealthRecordResponse res = new HealthRecordResponse();
        res.id = entity.getId();
        res.weightKg = entity.getWeightKg();
        res.memo = entity.getMemo();
        res.recordedAt = entity.getRecordedAt();
        return res;
    }

    public Long getId() {
        return id;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public String getMemo() {
        return memo;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }
}
