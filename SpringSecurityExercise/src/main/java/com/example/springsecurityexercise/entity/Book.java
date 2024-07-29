package com.example.springsecurityexercise.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Book extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String title;

    private Boolean isBorrowed;

    private String description;

    public Book(Long id, String code, String title, Boolean isBorrowed, String description) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.isBorrowed = isBorrowed;
        this.description = description;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    Set<Author> authors = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
