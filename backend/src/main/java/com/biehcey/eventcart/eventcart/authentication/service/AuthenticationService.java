package com.biehcey.eventcart.eventcart.authentication.service;

import com.biehcey.eventcart.eventcart.authentication.dto.UserLoginDto;
import com.biehcey.eventcart.eventcart.authentication.dto.UserRegisterDto;
import com.biehcey.eventcart.eventcart.authentication.entity.Role;
import com.biehcey.eventcart.eventcart.authentication.entity.User;
import com.biehcey.eventcart.eventcart.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public User signup(UserRegisterDto dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        if(dto.getRole() == null){
            user.setRole(Role.CUSTOMER);
        }
        else {
            user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
        }

        return repository.save(user);
    }

    public User login(UserLoginDto dto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );
        return repository.findByEmail(dto.getEmail());
    }


}
