package com.aihealthcare.aihealthcare.domain.user;

import com.aihealthcare.aihealthcare.domain.Blood.BloodTestEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL Auto Increment
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    private String username;
    private String password;

    // "MALE", "FEMALE"
    private String gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "height_cm")
    private BigDecimal heightCm;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 양방향 매핑 (선택 사항이지만 추천)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BloodTestEntity> bloodTests = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

