package com.project.tklembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceptorDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
    private Boolean status;
}
