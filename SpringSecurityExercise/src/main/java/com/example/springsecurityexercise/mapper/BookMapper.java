package com.example.springsecurityexercise.mapper;

import com.example.springsecurityexercise.dto.AuthorDto;
import com.example.springsecurityexercise.dto.BookDto;
import com.example.springsecurityexercise.entity.Author;
import com.example.springsecurityexercise.entity.Book;

import java.util.Set;
import java.util.stream.Collectors;

public class BookMapper {
    public static BookDto toBookDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getCode(),
                book.getTitle(),
                book.getIsBorrowed(),
                book.getDescription(),
                AuthorMapper.toDtoSet(book.getAuthors())
/*                UserMapper.toUserDto(book.getUser())*/
        );
    }

    public static Book toBook(BookDto bookDto) {
        return new Book(
                bookDto.getId(),
                bookDto.getCode(),
                bookDto.getTitle(),
                bookDto.getIsBorrowed(),
                bookDto.getDescription()
        );
    }

    public static Set<BookDto> toDtoSet(Set<Book> books) {
        return books.stream()
                .map(BookMapper::toBookDto)
                .collect(Collectors.toSet());
    }
}
