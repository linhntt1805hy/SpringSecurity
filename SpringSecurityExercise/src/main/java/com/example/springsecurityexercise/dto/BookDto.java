package com.example.springsecurityexercise.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class BookDto {
    private Long id;

    private String code;

    private String title;

    private Boolean isBorrowed;

    private String description;

    private Set<AuthorDto> authors;

    private List<String> authorCodes;

    private UserDto user;

    public BookDto(Long id, String code, String title, Boolean isBorrowed, String description, Set<AuthorDto> authors) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.isBorrowed = isBorrowed;
        this.description = description;
        this.authors = authors;
//        this.user = user;
    }
}
