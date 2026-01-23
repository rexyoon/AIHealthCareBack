package com.aihealthcare.aihealthcare.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private Long userId;
    private String token;
    private String username;
    private String accessToken;
}
