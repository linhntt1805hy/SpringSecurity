package com.example.springsecurityexercise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractAuditingEntity {
    @CreatedBy
    @Column(nullable = false, updatable = false)
    @JsonIgnore
    private String createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonIgnore
    private LocalDateTime createdDate;

    @LastModifiedBy
    @JsonIgnore
    private String lastModifiedBy;

    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime lastModifiedDate;
}
