package com.example.springsecurityexercise.service.impl;

import com.example.springsecurityexercise.dto.BookDto;
import com.example.springsecurityexercise.entity.Author;
import com.example.springsecurityexercise.entity.Book;
import com.example.springsecurityexercise.entity.User;
import com.example.springsecurityexercise.exception.ConflictDataException;
import com.example.springsecurityexercise.exception.MissingDataException;
import com.example.springsecurityexercise.exception.ResourceNotFoundException;
import com.example.springsecurityexercise.mapper.BookMapper;
import com.example.springsecurityexercise.repository.AuthorRepository;
import com.example.springsecurityexercise.repository.BookRepository;
import com.example.springsecurityexercise.repository.UserRepository;
import com.example.springsecurityexercise.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final UserRepository userRepository;

    @Transactional
    @Override
    public BookDto createBook(BookDto bookDto) {
        Optional<Book> checkBookCode = bookRepository.findByCode(bookDto.getCode());
        String userName = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            userName = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        if (bookDto.getAuthorCodes() == null){
            throw new MissingDataException("A book must have at least one author");
        }
        if (checkBookCode.isPresent()) {
            throw new ConflictDataException("Book's code existed");
        } else {
            Book book = BookMapper.toBook(bookDto);
            book.setCreatedBy(userName);
            book.setCreatedDate(LocalDateTime.now());
            Set<Author> authors = new HashSet<>();
            for (int i = 0; i < bookDto.getAuthorCodes().size(); i++) {
                Optional<Author> author = authorRepository.findByCode(bookDto.getAuthorCodes().get(i));
                if (author.isPresent()) {
                    authors.add(author.get());
                } else {
                    throw new ResourceNotFoundException("Author's code not found");
                    /*authorRepository.save(new Author(bookDto.getAuthorCodes().get(i)));
                    authors.add(authorRepository.findByCode(bookDto.getAuthorCodes().get(i)).get());*/
                }
            }
            book.setAuthors(authors);
            book = bookRepository.save(book);
//            logAudit("book", book.getId(), "CREATE");
            return BookMapper.toBookDto(book);
        }
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<BookDto> bookDtos = new ArrayList<>();
        List<Book> books = bookRepository.findAll();
        /*if (books.size() > 0) {
            for (Book book: books) {
                BookDto bookDto = BookMapper.toBookDto(book);
                bookDtos.add(bookDto);
            }
            return bookDtos;
        }
        throw new ResourceNotFoundException("No books data found");*/
        for (Book book: books) {
            BookDto bookDto = BookMapper.toBookDto(book);
            bookDtos.add(bookDto);
        }
        return bookDtos;
    }

    @Override
    public BookDto borrowBook(String title, String username) {
        Book book = new Book();
        Optional<List<Book>> optionalBooks = bookRepository.findBooksByTitleAndIsBorrowedIsNull(title);
        Optional<User> user = userRepository.findByUsername(username);
        if (optionalBooks.isPresent() && optionalBooks.get().size() > 0) {
            List<Book> books = optionalBooks.get();
            if (books.size() > 0) {
                Random random = new Random();
                int index = random.nextInt(books.size()); // Get a random index
                book = books.get(index); // Return the book at the random index
            }
            book.setIsBorrowed(true);
            if (user.isPresent()) {
                book.setUser(user.get());
            }
            book = bookRepository.save(book);
            return BookMapper.toBookDto(book);
        } else {
            throw new ResourceNotFoundException("Book is unavailable to borrow");
        }
    }
}
