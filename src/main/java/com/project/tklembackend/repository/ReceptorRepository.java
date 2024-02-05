package com.project.tklembackend.repository;

import com.project.tklembackend.model.Receptor;
import com.project.tklembackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceptorRepository extends JpaRepository<Receptor,Long> {
    Optional<User> findByEmail(String email);
}
