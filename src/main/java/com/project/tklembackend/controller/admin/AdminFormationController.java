package com.project.tklembackend.controller.admin;

import com.project.tklembackend.dto.FormationDTO;
import com.project.tklembackend.model.Formation;
import com.project.tklembackend.service.GlobalService;
import com.project.tklembackend.service.admin.AdminFormationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/formation")
public class AdminFormationController {
    private final AdminFormationService adminFormationService;
    private final GlobalService globalService;
    @PostMapping("/add")
    public ResponseEntity<Formation> addFormation(@RequestBody FormationDTO formationDto){
        Formation formation = adminFormationService.addFormation(formationDto);
        return new ResponseEntity<>(formation, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<FormationDTO>> getAllFormations(){
        return new ResponseEntity<>(adminFormationService.getAllFormations(),HttpStatus.OK);
    }
    @PostMapping("/edit")
    public ResponseEntity<Formation> editFormations(@RequestBody FormationDTO formationDto){
        return new ResponseEntity<>(adminFormationService.editFormations(formationDto),HttpStatus.OK);
    }
    @PostMapping("/delete")
    public ResponseEntity<Map<String,String>> deleteFormations(@RequestBody Map<String,Long> request){
        adminFormationService.deleteFormations(request.get("id"));
        return new ResponseEntity<>(globalService.responseBuilder("Item Successfully Deleted"),HttpStatus.OK);
    }
    @PostMapping("/deleteall")
    public ResponseEntity<Map<String,String>> deleteAllFormations(){
       adminFormationService.deleteAllFormations();
        return new ResponseEntity<>(globalService.responseBuilder("All Items Successfully Deleted"),HttpStatus.OK);
    }
}
