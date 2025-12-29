package com.aihealthcare.aihealthcare.controller;

import com.aihealthcare.aihealthcare.domain.Gender;
import com.aihealthcare.aihealthcare.domain.user.User;
import com.aihealthcare.aihealthcare.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;

    // 1. 회원가입 (간단 버전)
    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody UserSignupRequest request) {
        User user = User.builder()
                .loginId(request.loginId)
                .password(request.password) // 실무에선 암호화 필수
                .username(request.username)
                .gender(request.gender)
                .birthDate(request.birthDate)
                .height(request.height)
                .build();
        return ResponseEntity.ok(userRepository.save(user).getId());
    }

    // 2. 내 정보 조회
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long userId) {
        return ResponseEntity.of(userRepository.findById(userId));
    }

    // DTO 클래스 (파일 분리 안 하고 여기에 내부 클래스로 둠 -> 편의상)
    @Data
    public static class UserSignupRequest {
        private String loginId;
        private String password;
        private String username;
        private Gender gender;
        private LocalDate birthDate;
        private Double height;
    }
}
