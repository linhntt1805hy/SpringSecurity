package com.example.springsecurityexercise.service.impl;

import com.example.springsecurityexercise.dto.AuthorDto;
import com.example.springsecurityexercise.entity.Author;
import com.example.springsecurityexercise.exception.ConflictDataException;
import com.example.springsecurityexercise.exception.ResourceNotFoundException;
import com.example.springsecurityexercise.mapper.AuthorMapper;
import com.example.springsecurityexercise.repository.AuthorRepository;
import com.example.springsecurityexercise.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto createAuthor(AuthorDto authorDto) {
        Optional<Author> author = authorRepository.findByCode(authorDto.getCode());
        String userName = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            userName = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        if (author.isPresent()){
            throw new ConflictDataException("Author's code existed!");
        } else {
            Author newAuthor = new Author();
            BeanUtils.copyProperties(authorDto, newAuthor);
            newAuthor.setCreatedBy(userName);
            newAuthor.setCreatedDate(LocalDateTime.now());
            authorRepository.save(newAuthor);
            BeanUtils.copyProperties(newAuthor, authorDto);
            return authorDto;
        }
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDto> authorDtos = new ArrayList<>();
        /*if (authors.size() > 0 ) {
            for (Author author: authors) {
                AuthorDto authorDto = AuthorMapper.toAuthorDto(author);
                authorDtos.add(authorDto);
            }
            return authorDtos;
        }
        throw new ResourceNotFoundException("No authors data found");*/
        for (Author author: authors) {
            AuthorDto authorDto = AuthorMapper.toAuthorDto(author);
            authorDtos.add(authorDto);
        }
        return authorDtos;
    }
}
