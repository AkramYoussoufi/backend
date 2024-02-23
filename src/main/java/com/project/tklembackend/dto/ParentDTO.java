package com.project.tklembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String cin;
    private Set<String> studentsNames = new HashSet<>();
    private Boolean status;
}
