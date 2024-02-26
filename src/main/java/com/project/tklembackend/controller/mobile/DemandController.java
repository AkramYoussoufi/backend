package com.project.tklembackend.controller.mobile;

import com.project.tklembackend.dto.DemandDTO;
import com.project.tklembackend.service.GlobalService;
import com.project.tklembackend.service.mobile.DemandService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/demand")
@AllArgsConstructor
@Slf4j
public class DemandController {
    private final DemandService demandService;
    private final GlobalService globalService;


    @GetMapping  ("admin/all/parent")
    public ResponseEntity<List<DemandDTO>> demandAddParent(){

        return new ResponseEntity<>(demandService.getAllDemands(),HttpStatus.OK);
    }

    @PostMapping ("admin/accept/parent")
    public ResponseEntity<Map<String,String>> acceptAddParent(@RequestBody Map<String, Long> request){
        return demandService.acceptAddParent(request.get("id"));
    }
    @PostMapping ("admin/decline/parent")
    public ResponseEntity<Map<String,String>> declineAddParent(@RequestBody Map<String, Long> request){
        return demandService.declineAddParent(request.get("id"));
    }

    @GetMapping("admin/size")
    public ResponseEntity<Map<String,Long>> sizeOfDemands(){
        return demandService.sizeOfDemands();
    }

    @PostMapping("edit/parent")
    public ResponseEntity<Map<String,String>> sendDemandForAddingStudent(@RequestBody Map<String,String> request) throws InstanceAlreadyExistsException {
        demandService.sendDemandForAddingStudent(request.get("massarCode"));
        return new ResponseEntity<>(globalService.responseBuilder("Demande envoyée avec succès, vous devrez maintenant attendre l'approbation de l'administrateur"), HttpStatus.OK);
    }
}
