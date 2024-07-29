package com.example.springsecurityexercise.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "books")
@Entity
public class Author extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String fullName;

    private LocalDate dateOfBirth;

    private String biography;

    public Author(Long id, String code, String fullName, LocalDate dateOfBirth, String biography) {
        this.id = id;
        this.code = code;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.biography = biography;
    }

    @ManyToMany(mappedBy = "authors")
    Set<Book> books = new HashSet<>();
}
