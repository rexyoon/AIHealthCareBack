package com.aihealthcare.aihealthcare.domain.health;

import com.aihealthcare.aihealthcare.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_logs")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class HealthLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;

    @Column(name = "weight_kg")
    private BigDecimal weightKg;

    @Column(name = "muscle_mass_kg")
    private BigDecimal muscleMassKg;

    @Column(name = "body_fat_percent")
    private BigDecimal bodyFatPercent;

    @Column(name = "bp_systolic")
    private Integer bpSystolic;

    @Column(name = "bp_diastolic")
    private Integer bpDiastolic;

    @Column(name = "glucose_fasting")
    private Integer glucoseFasting;

    @PrePersist
    protected void onCreate() {
        if (this.recordedAt == null) {
            this.recordedAt = LocalDateTime.now();
        }
    }
}
