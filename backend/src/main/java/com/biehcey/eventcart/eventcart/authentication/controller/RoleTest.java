package com.biehcey.eventcart.eventcart.authentication.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleTest {

    @GetMapping("/admin")
    public String admin(){
        return "ADMIN" + SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/seller")
    public String seller(){
        return "SELLER";
    }
}
