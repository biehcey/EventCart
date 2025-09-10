package com.biehcey.eventcart.eventcart.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {
    private String email;
    private String username;
    private String password;
    private String role;
}
