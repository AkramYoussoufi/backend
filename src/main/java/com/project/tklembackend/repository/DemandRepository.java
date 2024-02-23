package com.project.tklembackend.repository;

import com.project.tklembackend.model.Demand;
import com.project.tklembackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandRepository extends JpaRepository<Demand,Long> {

    boolean existsByEmail(String email);

    boolean existsByCin(String cin);

    Student findByStudents(Student student);
}
