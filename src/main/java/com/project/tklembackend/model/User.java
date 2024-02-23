package com.project.tklembackend.model;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    //(ADMINS/RECEPTOR/RECIEVER) DOESN'T USE EMAILS THEY USE ONLY USERNAMES FOR SECURITY PURPOSE
    @Column(unique = true)
    private String email;
    }
