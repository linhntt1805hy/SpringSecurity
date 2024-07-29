package com.example.springsecurityexercise.service;

import com.example.springsecurityexercise.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    AuthorDto createAuthor(AuthorDto authorDto);
    List<AuthorDto> getAllAuthors();
}
