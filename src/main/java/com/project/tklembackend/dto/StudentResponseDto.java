package com.project.tklembackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StudentResponseDto {
    private String name;
    private String CodeMassar;
    private String Formation;
    private String Created;
}
