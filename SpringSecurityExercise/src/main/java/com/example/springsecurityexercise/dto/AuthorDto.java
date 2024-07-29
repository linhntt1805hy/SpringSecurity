package com.example.springsecurityexercise.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor

public class AuthorDto {
    private Long id;

    private String code;

    private String fullName;

    private LocalDate dateOfBirth;

    private String biography;

    private Set<BookDto> books;

    public AuthorDto(Long id, String code, String fullName, LocalDate dateOfBirth, String biography) {
        this.id = id;
        this.code = code;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.biography = biography;
    }
}
