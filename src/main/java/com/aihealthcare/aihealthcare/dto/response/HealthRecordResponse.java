package com.aihealthcare.aihealthcare.dto.response;

import com.aihealthcare.aihealthcare.domain.health.HealthRecord;
import java.time.LocalDateTime;

public record HealthRecordResponse(
        Long recordId,
        Double bloodSugar,
        Double weight,
        Integer systolicBp,
        Integer diastolicBp,
        String note,
        LocalDateTime recordedAt
) {
    // Entity -> DTO 변환 편의 메서드
    public static HealthRecordResponse from(HealthRecord entity) {
        return new HealthRecordResponse(
                entity.getId(),
                entity.getBloodSugar(),
                entity.getWeight(),
                entity.getSystolicBp(),
                entity.getDiastolicBp(),
                entity.getNote(),
                entity.getRecordedAt()
        );
    }
}
