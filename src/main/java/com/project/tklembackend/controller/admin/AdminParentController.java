package com.project.tklembackend.controller.admin;

import com.project.tklembackend.dto.ParentDTO;
import com.project.tklembackend.service.admin.AdminParentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/parent")
@AllArgsConstructor
public class AdminParentController {
    private final AdminParentService adminParentService;

    @GetMapping("/all")
    public ResponseEntity<List<ParentDTO>> getAllParents(){
        List<ParentDTO> parentList = adminParentService.getAllParent();
        return new ResponseEntity<>(parentList, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<ParentDTO> addParent(@Valid @RequestBody ParentDTO parentDTO){
        adminParentService.addParent(parentDTO);
        return new ResponseEntity<>(parentDTO, HttpStatus.CREATED);
    }

    @PostMapping("/edit")
    public ResponseEntity<ParentDTO> editParent(@Valid @RequestBody ParentDTO parentDTO){
        adminParentService.editParent(parentDTO);
        return new ResponseEntity<>(parentDTO, HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String,String>> deleteParent(@RequestBody Map<String,Long> request){
        adminParentService.deleteParent(request.get("id"));
        Map<String,String> response = new HashMap<>();
        response.put("message","Successfully parent deleted");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/deleteall")
    public ResponseEntity<Map<String,String>> deleteAllParent(@RequestBody List<Map<String, Long>> request){
        adminParentService.deleteAllParent(request);
        Map<String,String> response = new HashMap<>();
        response.put("message","Successfully All parents are deleted");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/edit-password")
    public ResponseEntity<Map<String,String>> editParentPassword(@RequestBody ParentDTO parentDTO){
        adminParentService.editParentPassword(parentDTO);
        Map<String,String> response = new HashMap<>();
        response.put("message","Parent Password has successfully modified");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
