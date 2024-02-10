package com.project.tklembackend.controller.admin;

import com.project.tklembackend.model.Parent;
import com.project.tklembackend.model.Receptor;
import com.project.tklembackend.service.admin.AdminParentService;
import com.project.tklembackend.service.admin.AdminReceptorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/receptor")
@AllArgsConstructor
public class AdminReceptorController {
    private final AdminReceptorService adminReceptorService;

    @GetMapping("/all")
    public ResponseEntity<List<Receptor>> getAllReceptors(){
        List<Receptor> receptorList = adminReceptorService.getAllReceptors();
        return new ResponseEntity<>(receptorList, HttpStatus.OK);
    }
}
