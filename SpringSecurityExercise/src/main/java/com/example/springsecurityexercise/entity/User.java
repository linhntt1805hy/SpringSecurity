package com.example.springsecurityexercise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"roles", "books"})
@Entity
@Table(name = "users")
public class User extends AbstractAuditingEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    private String address;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    public User(Long id, String fullName, String address, String phoneNumber, String username) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.username = username;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Book> books;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<? extends GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        System.out.println("Authorities for user: " + authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return authorities;
/*        Collection<GrantedAuthority> authorities = roles.stream()
                .flatMap(role -> role.getFunctions().stream())
                .map(function -> new SimpleGrantedAuthority(function.getName()))
                .collect(Collectors.toSet());

        // In ra các quyền
        System.out.println("Authorities for user: " + authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return authorities;*/
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
