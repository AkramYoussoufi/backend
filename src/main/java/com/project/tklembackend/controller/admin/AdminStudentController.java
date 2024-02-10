package com.project.tklembackend.controller.admin;

import com.project.tklembackend.model.Student;
import com.project.tklembackend.service.admin.AdminParentService;
import com.project.tklembackend.service.admin.AdminStudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/student")
@AllArgsConstructor
public class AdminStudentController {
    private final AdminStudentService adminStudentService;

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> studentList = adminStudentService.getAllStudent();
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }
}