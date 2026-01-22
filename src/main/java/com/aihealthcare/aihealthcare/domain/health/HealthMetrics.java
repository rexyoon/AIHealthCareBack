package com.aihealthcare.aihealthcare.domain.health;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "health_metrics",
        indexes = {
                @Index(
                        name = "idx_health_metrics_user_date",
                        columnList = "user_id, metrics_date",
                        unique = true
                ),
                @Index(
                        name = "idx_health_metrics_date",
                        columnList = "metrics_date"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metrics_id") // 테이블의 PK 컬럼 이름에 맞춤
    private Long metricsId; // id 필드 이름을 metricsId로 변경

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "metrics_date", nullable = false)
    private LocalDate metricsDate;

    // --------- 주요 지표 ---------
    @Column(name = "testosterone_total")
    private Double testosteroneTotal;

    @Column(name = "testosterone_free")
    private Double testosteroneFree;

    @Column(name = "estradiol_e2")
    private Double estradiolE2;

    @Column(name = "ast")
    private Double ast;

    @Column(name = "alt")
    private Double alt;

    // 프론트 카드 도넛%용 (0~100)
    @Column(name = "health_score")
    private Integer healthScore;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.healthScore == null) this.healthScore = 70;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
