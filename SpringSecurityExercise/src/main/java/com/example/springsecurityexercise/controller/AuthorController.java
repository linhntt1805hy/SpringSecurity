package com.example.springsecurityexercise.controller;

import com.example.springsecurityexercise.dto.AuthorDto;
import com.example.springsecurityexercise.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping()
    @PreAuthorize("hasAuthority('Librarian') OR hasAuthority('Borrower')")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorDto> authorDtos = authorService.getAllAuthors();
        return new ResponseEntity<>(authorDtos, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Librarian')")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        System.out.println("Run create Author...................................................");
        AuthorDto dto = authorService.createAuthor(authorDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

}
