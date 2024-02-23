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
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Parent extends User {
    @NotBlank(message="name is mandatory")
    private String name;
    @Column(unique=true)
    private String cin;
    @ManyToMany(mappedBy = "parents",fetch=FetchType.EAGER)
    private List<Student> students = new ArrayList<>();
    @OneToOne(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true,fetch=FetchType.EAGER)
    private UserEntity userEntity;
}
