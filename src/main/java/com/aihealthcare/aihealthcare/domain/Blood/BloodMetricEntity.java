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

    // --- [장기 기능 패널] ---
    private BigDecimal ast;
    private BigDecimal alt;

    @Column(name = "gamma_gtp")
    private BigDecimal gammaGtp;

    private BigDecimal bun;
    private BigDecimal creatinine;
    private BigDecimal egfr;

    // --- [혈액 성상 패널] ---
    private BigDecimal hemoglobin;
    private BigDecimal hematocrit;

    // --- [대사 패널] ---
    @Column(name = "cholesterol_total")
    private BigDecimal cholesterolTotal;

    private BigDecimal hdl;
    private BigDecimal ldl;
    private BigDecimal triglycerides;
}
