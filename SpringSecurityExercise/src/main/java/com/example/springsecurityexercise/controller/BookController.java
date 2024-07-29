package com.example.springsecurityexercise.controller;

import com.example.springsecurityexercise.dto.BookDto;
import com.example.springsecurityexercise.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    public final BookService bookService;

    @GetMapping
    @PreAuthorize("hasAuthority('Librarian') OR hasAuthority('Borrower')")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> bookDtos = bookService.getAllBooks();
        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('Librarian')")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto dto = bookService.createBook(bookDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/borrowBook")
    @PreAuthorize("hasAuthority('Borrower')")
    public ResponseEntity<BookDto> updateUserBorrowBook(@RequestBody BookDto bookDto) {
        String userName = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            userName = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        BookDto dto = bookService.borrowBook(bookDto.getTitle(), userName);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
