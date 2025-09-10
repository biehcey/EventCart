package com.biehcey.eventcart.eventcart.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private Long expiresIn;
    private LocalDateTime createdAt;
    private String role;
}
