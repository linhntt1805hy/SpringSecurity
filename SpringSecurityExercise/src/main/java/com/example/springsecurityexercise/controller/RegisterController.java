package com.example.springsecurityexercise.controller;

import com.example.springsecurityexercise.dto.UserDto;
import com.example.springsecurityexercise.repository.UserRepository;
import com.example.springsecurityexercise.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto dto = userService.createUser(userDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
