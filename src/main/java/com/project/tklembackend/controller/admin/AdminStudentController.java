package com.project.tklembackend.controller.admin;

import com.project.tklembackend.dto.StudentRequestDto;
import com.project.tklembackend.dto.StudentResponseDto;
import com.project.tklembackend.model.Student;
import com.project.tklembackend.service.admin.AdminStudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/student")
@AllArgsConstructor
public class AdminStudentController {
    private final AdminStudentService adminStudentService;

    @GetMapping("/all")
    public ResponseEntity<List<StudentResponseDto>> getAllStudents(){
        List<StudentResponseDto> responseList = adminStudentService.getAllStudent();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody StudentRequestDto studentRequestDto) throws InstanceAlreadyExistsException {
        Student student = adminStudentService.addStudent(studentRequestDto);
        return new ResponseEntity<>(student,HttpStatus.CREATED);
    }
}