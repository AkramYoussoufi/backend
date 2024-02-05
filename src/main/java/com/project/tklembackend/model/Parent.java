package com.project.tklembackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

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
    private String cin;
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
    private String phone;
    @ManyToMany(mappedBy = "parentList")
    private List<Student> studentList = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.REMOVE)
    private UserEntity userEntity;

}
