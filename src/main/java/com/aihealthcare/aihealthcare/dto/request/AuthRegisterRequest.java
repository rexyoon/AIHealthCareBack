package com.aihealthcare.aihealthcare.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class AuthRegisterRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(max = 100)
    private String username;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

    private String gender;
    private LocalDate birthDate;
    private BigDecimal heightCm;


}
