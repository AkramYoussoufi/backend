package com.project.tklembackend.repository;

import com.project.tklembackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByMassarCode(String massarCode);

    Optional<Student> findByName(String name);

    Optional<Student> findByMassarCode(String code);
}
