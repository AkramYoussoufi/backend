package com.project.tklembackend.repository;

import com.project.tklembackend.model.Reciever;
import com.project.tklembackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecieverRepository extends JpaRepository<Reciever,Long> {
    Optional<User> findByEmail(String email);
}
