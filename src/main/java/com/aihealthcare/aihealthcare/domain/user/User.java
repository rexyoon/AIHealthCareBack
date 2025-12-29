package com.aihealthcare.aihealthcare.domain.user;

import com.aihealthcare.aihealthcare.domain.Gender;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(nullable = false, unique = true)
    private String loginId;
    @Column(nullable = false)
    private String password;
    private String username;
    @Enumerated(EnumType.STRING)
    private Gender gender; // M, F
    private LocalDate birthDate;
    private Double height;
    @Builder
    public User(String loginId, String password, String username, Gender gender, LocalDate birthDate, Double height) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.gender = gender;
        this.birthDate = birthDate;
        this.height = height;
    }
    // 정보 수정용 메서드
    public void updateInfo(Double height, String username) {
        this.height = height;
        this.username = username;
    }
}
