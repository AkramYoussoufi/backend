package com.project.tklembackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank(message="name is mandatory")
    private String name;
    @Column(unique = true)
    @NotBlank(message="Massar Code is mandatory")
    private String massarCode;
    @ManyToOne(fetch = FetchType.EAGER)
    private Formation formation;
    @ManyToMany(fetch=FetchType.EAGER)
    private List<Parent> parents = new ArrayList<>();

}
