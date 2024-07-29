package com.example.springsecurityexercise.service;

import com.example.springsecurityexercise.dto.LoginDto;
import com.example.springsecurityexercise.dto.UserDto;
import com.example.springsecurityexercise.entity.User;

public interface UserService {
    User login(LoginDto loginDto);
    UserDto createUser(UserDto userDto);
}
