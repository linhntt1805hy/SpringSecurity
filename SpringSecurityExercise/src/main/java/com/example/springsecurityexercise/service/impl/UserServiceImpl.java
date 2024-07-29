package com.example.springsecurityexercise.service.impl;

import com.example.springsecurityexercise.dto.LoginDto;
import com.example.springsecurityexercise.dto.UserDto;
import com.example.springsecurityexercise.entity.Role;
import com.example.springsecurityexercise.entity.User;
import com.example.springsecurityexercise.exception.BadCredentialException;
import com.example.springsecurityexercise.exception.ConflictDataException;
import com.example.springsecurityexercise.exception.MissingDataException;
import com.example.springsecurityexercise.exception.ResourceNotFoundException;
import com.example.springsecurityexercise.mapper.UserMapper;
import com.example.springsecurityexercise.repository.RoleRepository;
import com.example.springsecurityexercise.repository.UserRepository;
import com.example.springsecurityexercise.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Override
    public User login(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );
        } catch (Exception ex) {
            throw new BadCredentialException("The username or password is incorrect");
        }
        Optional<User> user = userRepository.findByUsername(loginDto.getUsername());
        return user.get();
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Optional<User> user = userRepository.findByUsername(userDto.getUsername());
        if (userDto.getFullName() == null) {
            throw new MissingDataException("Fullname is not null");
        }
        if (user.isPresent()) {
            throw new ConflictDataException("Username existed!");
        }
        User newUser = UserMapper.toUser(userDto);
        Optional<Role> role = roleRepository.findByName("Borrower");
        newUser.setCreatedBy(userDto.getUsername());
        newUser.setCreatedDate(LocalDateTime.now());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRoles(Set.of(role.get()));
        User dto = userRepository.save(newUser);
        return UserMapper.toUserDto(dto);
    }
}
