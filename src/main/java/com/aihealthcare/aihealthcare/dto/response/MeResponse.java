package com.aihealthcare.aihealthcare.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeResponse {
    private Long userId;
    private String email;
    private String firstName;
}
