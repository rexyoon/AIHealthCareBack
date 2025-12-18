package com.aihealthcare.aihealthcare.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "health_record")
public class HealthRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double weightKg;

    @Column(length = 500)
    private String memo;

    @Column(nullable = false)
    private LocalDateTime recordedAt;

    protected HealthRecordEntity() {
        // JPA 기본 생성자
    }

    private HealthRecordEntity(Double weightKg, String memo, LocalDateTime recordedAt) {
        this.weightKg = weightKg;
        this.memo = memo;
        this.recordedAt = recordedAt;
    }

    public static HealthRecordEntity create(Double weightKg, String memo) {
        return new HealthRecordEntity(weightKg, memo, LocalDateTime.now());
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
