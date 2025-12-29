package com.aihealthcare.aihealthcare.domain;

import com.aihealthcare.aihealthcare.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ai_analysis_logs")
public class AiAnalysisLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private HealthRecord healthRecord; // 어떤 기록에 대한 조언인지

    private String healthStatus; // 상태 요약 (주의, 양호 등)

    @Column(columnDefinition = "TEXT")
    private String exerciseAdvice;

    @Column(columnDefinition = "TEXT")
    private String dietAdvice;

    private LocalDateTime createdAt;

    @Builder
    public AiAnalysisLog(User user, HealthRecord healthRecord, String healthStatus, String exerciseAdvice, String dietAdvice) {
        this.user = user;
        this.healthRecord = healthRecord;
        this.healthStatus = healthStatus;
        this.exerciseAdvice = exerciseAdvice;
        this.dietAdvice = dietAdvice;
        this.createdAt = LocalDateTime.now();
    }
}
