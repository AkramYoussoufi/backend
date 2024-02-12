package com.project.tklembackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank(message="name is mandatory")
    @Column(unique = true)
    private String name;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Student> studentList;
    @OneToMany
    private List<Reciever> recieverList;


}
