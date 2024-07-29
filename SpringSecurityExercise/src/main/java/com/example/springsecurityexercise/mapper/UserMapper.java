package com.example.springsecurityexercise.mapper;

import com.example.springsecurityexercise.dto.UserDto;
import com.example.springsecurityexercise.entity.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFullName(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getUsername(),
                user.getPassword()
        );
    }

    public static User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getFullName(),
                userDto.getAddress(),
                userDto.getPhoneNumber(),
                userDto.getUsername()
        );
    }
}
