package com.aihealthcare.aihealthcare.domain.Blood;
import com.aihealthcare.aihealthcare.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "blood_tests")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class BloodTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Long testId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "test_date", nullable = false)
    private LocalDate testDate;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "ai_summary", columnDefinition = "TEXT")
    private String aiSummary;

    @Column(name = "risk_level")
    private String riskLevel; // NORMAL, WARNING, RISK

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 1:1 관계 매핑 (BloodMetric이 주인이 아니므로 mappedBy 사용)
    @OneToOne(mappedBy = "bloodTest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BloodMetric bloodMetric;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
