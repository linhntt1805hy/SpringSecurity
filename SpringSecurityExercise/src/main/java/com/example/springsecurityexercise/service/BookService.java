package com.example.springsecurityexercise.service;

import com.example.springsecurityexercise.dto.AuthorDto;
import com.example.springsecurityexercise.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookDto);
    List<BookDto> getAllBooks();
    BookDto borrowBook(String title, String username);
}
