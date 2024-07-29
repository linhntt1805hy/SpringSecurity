package com.example.springsecurityexercise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    private String fullName;

    private String address;

    private String phoneNumber;

    private String username;

    private String password;
}
