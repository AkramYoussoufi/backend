package com.project.tklembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StudentRequestDto {
    private Long id;
    private String massarCode;
    private String name;
    private String formationName;
}
