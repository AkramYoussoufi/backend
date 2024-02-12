package com.project.tklembackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.Instant;

@MappedSuperclass
@Data
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Email
    @Column(unique = true)
    private String email;
    @Column
    private Instant createdOn = Instant.now();
    @Column
    private Instant updatedOn = Instant.now();
}
