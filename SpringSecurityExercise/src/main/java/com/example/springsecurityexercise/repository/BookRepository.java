package com.example.springsecurityexercise.repository;

import com.example.springsecurityexercise.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByCode(String code);
    Optional<List<Book>> findBooksByTitleAndIsBorrowedIsNull(String title);
}
