package com.project.tklembackend.controller.admin;

import com.project.tklembackend.dto.FormationDto;
import com.project.tklembackend.model.Formation;
import com.project.tklembackend.service.admin.AdminFormationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/formation")
public class AdminFormationController {
    private final AdminFormationService adminFormationService;
    @PostMapping("/add")
    public ResponseEntity<Formation> addFormation(@RequestBody FormationDto formationDto){
        Formation formation = adminFormationService.addFormation(formationDto);
        return new ResponseEntity<>(formation, HttpStatus.CREATED);
    }
}
