package com.project.tklembackend.controller;

import com.project.tklembackend.dto.RegisterRequest;
import com.project.tklembackend.dto.SignupRequest;
import com.project.tklembackend.service.AuthService;
import jakarta.mail.AuthenticationFailedException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.InstanceAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/signin")
    public ResponseEntity<Map<String,String>> signIn(@RequestBody RegisterRequest registerRequest) throws InstanceAlreadyExistsException {
        authService.signIn(registerRequest);
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "User Successfully created!");
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String,String>> signUp(@RequestBody SignupRequest signupRequest) throws AuthenticationFailedException {
        String token = authService.authenticateUser(signupRequest);
        HashMap<String,String> response = new HashMap<>();
        response.put("token",token);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}