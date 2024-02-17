package com.project.tklembackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@NoArgsConstructor
@Data
public class StudentResponseDto {
    private Long id;
    private String name;
    private String massarCode;
    private String formationName;
    private String Created;
}
