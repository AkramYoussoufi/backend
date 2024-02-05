package com.project.tklembackend.model;


import jakarta.persistence.*;
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
public class Reciever extends User {

    @ManyToOne
    private Formation formation;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private UserEntity userEntity;
}
