package com.project.tklembackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Receptor extends User {
    @NotBlank(message="name is mandatory")
    private String name;
    @OneToOne(mappedBy = "receptor", cascade = CascadeType.ALL, orphanRemoval = true,fetch=FetchType.EAGER)
    private UserEntity userEntity;
}
