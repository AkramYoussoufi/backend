package com.project.tklembackend.controller;

import com.project.tklembackend.service.GlobalService;
import com.project.tklembackend.service.admin.AdminRecieverService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/reciever")
@AllArgsConstructor

public class RecieverController {
    private final GlobalService globalService;
    private final AdminRecieverService adminRecieverService;
    @GetMapping("current")
    public ResponseEntity<Map<String,String>> getCurrentReciever(){
        return new ResponseEntity<>(globalService.responseBuilder(adminRecieverService.getCurrentReciever()), HttpStatus.OK);
    }
}
