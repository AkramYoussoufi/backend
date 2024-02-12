package com.project.tklembackend.repository;

import com.project.tklembackend.model.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormationRepository extends JpaRepository<Formation,Long> {
    Optional<Formation> findByName(String formationName);
}
