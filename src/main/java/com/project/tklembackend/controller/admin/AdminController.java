package com.project.tklembackend.controller.admin;

import com.project.tklembackend.dto.UserDTO;
import com.project.tklembackend.service.admin.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Map<String,String>> addAdmin(@RequestBody UserDTO userDTO){
        adminService.addAdmin(userDTO);
        HashMap<String,String> response = new HashMap<>();
        response.put("message","L'utilisateur admin est ajouté");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/edit")
    public ResponseEntity<Map<String,String>> editAdmin(@RequestBody UserDTO userDTO){
        adminService.editAdmin(userDTO);
        HashMap<String,String> response = new HashMap<>();
        response.put("message","L'utilisateur admin édité avec succès");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PostMapping("/delete")
    public ResponseEntity<Map<String,String>> deleteAdmin(@RequestBody Map<String, Long> requestBody){
        adminService.deleteAdmin(requestBody.get("id"));
        HashMap<String,String> response = new HashMap<>();
        response.put("message","L'utilisateur admin supprimé avec succès");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
