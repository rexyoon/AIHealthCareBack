package com.aihealthcare.aihealthcare.domain.health;

import com.aihealthcare.aihealthcare.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "health_records")
public class HealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long id;

    // [누구의 기록인가?]
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // [4대 건강 지표]
    private Double bloodSugar;      // 혈당
    private Double weight;          // 몸무게
    private Integer systolicBp;     // 최고 혈압
    private Integer diastolicBp;    // 최저 혈압

    @Column(columnDefinition = "TEXT")
    private String note;            // 메모

    private LocalDateTime recordedAt; // 측정 일시

    @Builder
    public HealthRecord(User user, Double bloodSugar, Double weight, Integer systolicBp, Integer diastolicBp, String note) {
        this.user = user;
        this.bloodSugar = bloodSugar;
        this.weight = weight;
        this.systolicBp = systolicBp;
        this.diastolicBp = diastolicBp;
        this.note = note;
        this.recordedAt = LocalDateTime.now(); // 생성 시 현재 시간 자동 저장
    }

    // 수정(Update)을 위한 메서드
    public void update(Double bloodSugar, Double weight, Integer systolicBp, Integer diastolicBp, String note) {
        this.bloodSugar = bloodSugar;
        this.weight = weight;
        this.systolicBp = systolicBp;
        this.diastolicBp = diastolicBp;
        this.note = note;
    }
}
