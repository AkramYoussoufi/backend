package com.project.tklembackend.controller.mobile;

import com.project.tklembackend.dto.StudentDTO;
import com.project.tklembackend.service.GlobalService;
import com.project.tklembackend.service.admin.AdminStudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@AllArgsConstructor
public class StudentController {
    private AdminStudentService adminStudentService;
    private GlobalService globalService;
    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        List<StudentDTO> responseList = adminStudentService.getAllStudent();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/mystudents")
    public ResponseEntity<List<StudentDTO>> getMyStudents(){
        return new ResponseEntity<>(adminStudentService.getMyStudents(),HttpStatus.OK);
    }

    @PostMapping("/deletestfrpr")
    public ResponseEntity<Map<String,String>> deleteStudentFromParent(@RequestBody Map<String,String> response){
        adminStudentService.deleteStudentFromParent(response.get("massarCode"));
        return new ResponseEntity<>(globalService.responseBuilder("Successfully removed from parent"),HttpStatus.OK);
    }
}
