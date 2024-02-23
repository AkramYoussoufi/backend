package com.project.tklembackend.controller.admin;

import com.project.tklembackend.dto.StudentDTO;
import com.project.tklembackend.model.Student;
import com.project.tklembackend.service.admin.AdminStudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/student")
@AllArgsConstructor
public class AdminStudentController {
    private final AdminStudentService adminStudentService;

    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        List<StudentDTO> responseList = adminStudentService.getAllStudent();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody StudentDTO studentDTO) throws InstanceAlreadyExistsException {
        Student student = adminStudentService.addStudent(studentDTO);
        return new ResponseEntity<>(student,HttpStatus.CREATED);
    }

    @PostMapping("/edit")
    public ResponseEntity<Student> editStudent(@RequestBody StudentDTO studentDTO) {
        Student student = adminStudentService.editStudent(studentDTO);
        return new ResponseEntity<>(student,HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String,String>> deleteStudent(@RequestBody Map<String, Long> requestBody)  {
        adminStudentService.deleteStudent(requestBody.get("id"));
        HashMap<String,String> response = new HashMap<>();
        response.put("message","Student requested is deleted");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/deleteall")
    public ResponseEntity<Map<String,String>> deleteAllStudent(@RequestBody ArrayList<Long> requestBody)  {
        adminStudentService.deleteAllStudent(requestBody);
        HashMap<String,String> response = new HashMap<>();
        response.put("message","All Selected Student are deleted successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}