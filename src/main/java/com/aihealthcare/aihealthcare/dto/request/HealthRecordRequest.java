package com.aihealthcare.aihealthcare.dto.request;

public record HealthRecordRequest(
        Long userId,            // 누가 기록하는지
        Double bloodSugar,      // 혈당
        Double weight,          // 몸무게
        Integer systolicBp,     // 최고 혈압
        Integer diastolicBp,    // 최저 혈압
        String note             // 메모
) {}
