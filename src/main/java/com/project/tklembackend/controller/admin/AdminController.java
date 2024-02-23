package com.project.tklembackend.controller.admin;

import com.project.tklembackend.dto.UserDTO;
import com.project.tklembackend.service.admin.AdminService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/user")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/all")
    public List<UserDTO> getAllAdmin(){
        return adminService.getAllAdmin();
    }

    @PostMapping("/add")
    public ResponseEntity<UserDTO> addAdmin(@Valid @RequestBody UserDTO userDTO) throws InstanceAlreadyExistsException {
        return new ResponseEntity<>(adminService.addAdmin(userDTO), HttpStatus.CREATED);
    }
    @PostMapping("/edit")
    public ResponseEntity<UserDTO> editAdmin(@Valid @RequestBody UserDTO userDTO){
        return new ResponseEntity<>(adminService.editAdmin(userDTO),HttpStatus.OK);
    }
    @PostMapping("/delete")
    public ResponseEntity<Map<String,String>> deleteAdmin(@RequestBody Map<String, Long> requestBody){
        adminService.deleteAdmin(requestBody.get("id"));
        HashMap<String,String> response = new HashMap<>();
        response.put("message","L'utilisateur admin supprimé avec succès");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
