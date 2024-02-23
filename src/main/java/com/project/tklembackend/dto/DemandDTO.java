package com.project.tklembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DemandDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String cin;
    private ArrayList<String> studentsMassarCode = new ArrayList<>();
    private ArrayList<StudentDTO> students = new ArrayList<>();
}
