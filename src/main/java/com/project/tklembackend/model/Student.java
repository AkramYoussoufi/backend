package com.project.tklembackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank(message="name is mandatory")
    private String name;
    private String massarCode;
    @ManyToOne(fetch = FetchType.EAGER)
    private Formation formation;
    @ManyToMany
    private List<Parent> parentList = new ArrayList<>();

}
