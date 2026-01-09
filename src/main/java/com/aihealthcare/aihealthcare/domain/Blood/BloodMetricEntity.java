package com.aihealthcare.aihealthcare.domain.Blood;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "blood_metrics")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class BloodMetricEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metric_id")
    private Long metricId;

    // 1:1 관계의 주인 (Foreign Key를 가짐)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false, unique = true)
    private BloodTestEntity bloodTest;

    // --- [호르몬 패널] ---
    @Column(name = "testosterone_total")
    private BigDecimal testosteroneTotal;

    @Column(name = "testosterone_free")
    private BigDecimal testosteroneFree;

    @Column(name = "estradiol_e2")
    private BigDecimal estradiolE2;

    private BigDecimal prolactin;
    private BigDecimal lh;
    private BigDecimal fsh;
    private BigDecimal shbg;
    private BigDecimal cortisol;

    // --- [갑상선 패널] ---
    private BigDecimal tsh; // TSH
    private BigDecimal t3;  // T3
    private BigDecimal t4;  // T4

    // --- [장기 기능 패널] ---
    private BigDecimal ast;
    private BigDecimal alt;

    @Column(name = "gamma_gtp")
    private BigDecimal gammaGtp;

    private BigDecimal bun; // Blood Urea Nitrogen
    private BigDecimal creatinine;
    private BigDecimal egfr;

    // --- [혈액 성상 패널] ---
    private BigDecimal hemoglobin;
    private BigDecimal hematocrit;

    // --- [대사 패널] ---
    @Column(name = "cholesterol_total")
    private BigDecimal cholesterolTotal;

    private BigDecimal hdl; // High-Density Lipoprotein
    private BigDecimal ldl; // Low-Density Lipoprotein
    private BigDecimal triglycerides;

    // --- [전해질 패널] ---
    private BigDecimal sodium;     // 나트륨
    private BigDecimal potassium;   // 칼륨
    private BigDecimal magnesium;   // 마그네슘

    // --- [렙틴 패널] ---
    private BigDecimal leptin; // 렙틴

    // --- [기타 패널] ---
    private BigDecimal hba1c; // 당화혈색소
    private BigDecimal crp;    // C-Reactive Protein
}
