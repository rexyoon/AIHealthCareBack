package com.aihealthcare.aihealthcare.service;

import com.aihealthcare.aihealthcare.auth.jwt.JwtProvider;
import com.aihealthcare.aihealthcare.domain.user.UserEntity;
import com.aihealthcare.aihealthcare.dto.request.AuthLoginRequest;
import com.aihealthcare.aihealthcare.dto.request.AuthRegisterRequest;
import com.aihealthcare.aihealthcare.dto.response.AuthResponse;
import com.aihealthcare.aihealthcare.dto.response.MeResponse;
import com.aihealthcare.aihealthcare.exception.ApiException;
import com.aihealthcare.aihealthcare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public AuthResponse register(AuthRegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new ApiException(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다.");
        }

        UserEntity user = UserEntity.builder()
                .email(req.getEmail())
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .gender(req.getGender())
                .birthDate(req.getBirthDate())
                .heightCm(req.getHeightCm())
                .build();

        UserEntity saved = userRepository.save(user);

        String token = jwtProvider.generateAccessToken(saved.getUserId(), saved.getEmail());

        return new AuthResponse(
                saved.getUserId(),
                saved.getEmail(),
                saved.getUsername(),
                token
        );
    }

    @Transactional(readOnly = true)
    public AuthResponse login(AuthLoginRequest req) {
        UserEntity user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtProvider.generateAccessToken(user.getUserId(), user.getEmail());

        return new AuthResponse(
                user.getUserId(),
                user.getEmail(),
                user.getUsername(),
                token
        );
    }

    @Transactional(readOnly = true)
    public MeResponse me(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "유효하지 않은 사용자입니다."));

        return new MeResponse(user.getUserId(), user.getEmail(), user.getUsername());
    }
}
