package com.example.springsecurityexercise.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "users")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "role_function",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "function_id")
    )
    private Set<Function> functions = new HashSet<>();
}
