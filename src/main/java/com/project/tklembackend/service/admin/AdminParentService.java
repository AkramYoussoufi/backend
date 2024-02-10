package com.project.tklembackend.service.admin;

import com.project.tklembackend.model.Parent;
import com.project.tklembackend.repository.ParentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminParentService {
    private final ParentRepository parentRepository;

    public List<Parent> getAllParent(){
        return parentRepository.findAll();
    }
}
