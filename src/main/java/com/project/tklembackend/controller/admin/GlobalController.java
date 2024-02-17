package com.project.tklembackend.controller.admin;

import com.project.tklembackend.dto.UserDTO;
import com.project.tklembackend.model.UserEntity;
import com.project.tklembackend.service.AuthService;
import com.project.tklembackend.service.GlobalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class GlobalController {
    private final AuthService authService;
    private final GlobalService globalService;
    @GetMapping("/currentuser")
    public ResponseEntity<UserDTO> getCurrentLoggedUser(){
        return new ResponseEntity<>(globalService.adminToAdminDTO((UserEntity) authService.getCurrentAuthenticatedUser().get()), HttpStatus.OK);
    }
}
