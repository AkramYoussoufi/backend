package com.project.tklembackend.repository;

import com.project.tklembackend.model.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationRepository extends JpaRepository<Formation,Long> {
}
