package com.project.tklembackend.repository;

import com.project.tklembackend.model.StudentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface StudentLogRepository extends JpaRepository<StudentLog,Long> {
    List<StudentLog> findByDateBefore(Instant date);
}
