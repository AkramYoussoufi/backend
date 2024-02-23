package com.project.tklembackend.controller.admin;

import com.project.tklembackend.dto.RecieverDTO;
import com.project.tklembackend.service.GlobalService;
import com.project.tklembackend.service.admin.AdminRecieverService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/reciever")
@AllArgsConstructor
public class AdminRecieverController {
    private final AdminRecieverService adminRecieverService;
    private final GlobalService globalService;

    @GetMapping("/all")
    public ResponseEntity<List<RecieverDTO>> getAllRecievers(){
        return new ResponseEntity<>(adminRecieverService.getAllRecievers(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<RecieverDTO> addReciever(@RequestBody RecieverDTO RecieverDTO){
        return new ResponseEntity<>(adminRecieverService.addReciever(RecieverDTO), HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<RecieverDTO> editReciever(@RequestBody RecieverDTO RecieverDTO){
        return new ResponseEntity<>(adminRecieverService.editReciever(RecieverDTO), HttpStatus.OK);
    }
    @PostMapping("/delete")
    public ResponseEntity<Map<String,String>> deleteReciever(@RequestBody Map<String,Long> request){
        adminRecieverService.deleteRecievers(request.get("id"));
        return new ResponseEntity<>(globalService.responseBuilder("entity deleted Successfully"), HttpStatus.OK);
    }

    @PostMapping("/deleteall")
    public ResponseEntity<Map<String,String>> deleteReciever(@RequestBody List<Map<String,Long>> request){
        request.forEach(id->{adminRecieverService.deleteRecievers(id.get("id"));});
        return new ResponseEntity<>(globalService.responseBuilder("entity deleted Successfully"), HttpStatus.OK);
    }
}

