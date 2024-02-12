package com.project.tklembackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
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
    @ManyToMany
    private List<Parent> parentList = new ArrayList<>();
    @Column
    private Instant createdOn = Instant.now();
    @Column
    private Instant updatedOn = Instant.now();

}
