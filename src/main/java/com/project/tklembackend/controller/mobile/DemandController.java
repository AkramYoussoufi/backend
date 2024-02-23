package com.project.tklembackend.controller.mobile;

import com.project.tklembackend.dto.DemandDTO;
import com.project.tklembackend.model.Demand;
import com.project.tklembackend.service.mobile.DemandService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/demand")
@AllArgsConstructor
public class DemandController {
    private final DemandService demandService;

    @GetMapping  ("admin/all/parent")
    public List<Demand> demandAddParent(){
        return demandService.getAllDemands();
    }
    @PostMapping ("add/parent")
    public ResponseEntity<Map<String,String>> demandAddParent(@RequestBody DemandDTO demandDTO) throws InstanceAlreadyExistsException {
        return demandService.demandAddParent(demandDTO);
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
}
