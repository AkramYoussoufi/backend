package com.project.tklembackend.controller.admin;

import com.project.tklembackend.model.Reciever;
import com.project.tklembackend.service.admin.AdminParentService;
import com.project.tklembackend.service.admin.AdminRecieverService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reciever")
@AllArgsConstructor
public class AdminRecieverController {
    private final AdminRecieverService adminRecieverService;

    @GetMapping("/all")
    public ResponseEntity<List<Reciever>> getAllRecievers(){
        List<Reciever> recieverList = adminRecieverService.getAllReciever();
        return new ResponseEntity<>(recieverList, HttpStatus.OK);
    }
}

