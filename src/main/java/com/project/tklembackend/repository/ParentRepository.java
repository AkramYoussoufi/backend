package com.project.tklembackend.repository;

import com.project.tklembackend.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    Optional<Parent> findByEmail(String email);

    boolean existsByCin(String cin);

    boolean existsByEmail(String email);
}
