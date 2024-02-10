package com.project.tklembackend.controller.admin;

import com.project.tklembackend.model.Parent;
import com.project.tklembackend.service.admin.AdminParentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/parent")
@AllArgsConstructor
public class AdminParentController {
    private final AdminParentService adminParentService;

    @GetMapping("/all")
    public ResponseEntity<List<Parent>> getAllParents(){
        List<Parent> parentList = adminParentService.getAllParent();
        return new ResponseEntity<>(parentList, HttpStatus.OK);
    }
}
