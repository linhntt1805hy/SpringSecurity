package com.example.springsecurityexercise.mapper;

import com.example.springsecurityexercise.dto.AuthorDto;
import com.example.springsecurityexercise.entity.Author;

import java.util.Set;
import java.util.stream.Collectors;

public class AuthorMapper {
    public static AuthorDto toAuthorDto(Author author) {
        return new AuthorDto(
                author.getId(),
                author.getCode(),
                author.getFullName(),
                author.getDateOfBirth(),
                author.getBiography()
//                BookMapper.toDtoSet(author.getBooks())
        );
    }

    public Author toAuthor(AuthorDto authorDto) {
        return new Author(
                authorDto.getId(),
                authorDto.getCode(),
                authorDto.getFullName(),
                authorDto.getDateOfBirth(),
                authorDto.getBiography()
        );
    }

    public static Set<AuthorDto> toDtoSet(Set<Author> authors) {
        return authors.stream()
                .map(AuthorMapper::toAuthorDto)
                .collect(Collectors.toSet());
    }
}
