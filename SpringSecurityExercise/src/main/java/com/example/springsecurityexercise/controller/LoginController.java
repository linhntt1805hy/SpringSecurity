package com.example.springsecurityexercise.controller;

import com.example.springsecurityexercise.dto.LoginDto;
import com.example.springsecurityexercise.dto.LoginResponse;
import com.example.springsecurityexercise.entity.User;
import com.example.springsecurityexercise.security.JwtService;
import com.example.springsecurityexercise.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final JwtService jwtService;

    private final UserService loginService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto loginDto) {
        User loginUser = loginService.login(loginDto);
        String jwtToken = jwtService.generateToken(loginUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}
