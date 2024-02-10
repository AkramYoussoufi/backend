package com.project.tklembackend.service.admin;

import com.project.tklembackend.model.Student;
import com.project.tklembackend.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminStudentService {
    private final StudentRepository studentRepository;

    public List<Student> getAllStudent(){
        return studentRepository.findAll();
    }
}
