package com.aihealthcare.aihealthcare.domain.health;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "health_metrics",
        indexes = {
                @Index(name = "idx_health_metrics_user_date", columnList = "user_id, metrics_date", unique = true),
                @Index(name = "idx_health_metrics_date", columnList = "metrics_date")
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
    private Long id;

    // 로그인 붙이기 전 단계면 userId만으로도 충분
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "metrics_date", nullable = false)
    private LocalDate date;

    // --------- 주요 지표 (필요한 것만 먼저) ---------
    private Double testosteroneTotal;
    private Double testosteroneFree;
    private Double estradiolE2;

    private Double ast;
    private Double alt;

    // 프론트 카드 도넛%용 (0~100)
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
        if (this.healthScore == null) this.healthScore = 70; // 기본값
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
