package com.project.tklembackend.service.admin;

import com.project.tklembackend.model.Reciever;
import com.project.tklembackend.repository.RecieverRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminRecieverService {
    private final RecieverRepository recieverRepository;

    public List<Reciever> getAllReciever(){
        return recieverRepository.findAll();
    }
}
