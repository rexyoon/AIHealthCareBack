package com.aihealthcare.aihealthcare.dto.request;

public record HealthAnalysisRequest(
        double bloodSuger,
        double weight,
        int systolicPressure,
        int diastolicPressure
){}