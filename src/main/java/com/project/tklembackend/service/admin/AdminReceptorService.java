package com.project.tklembackend.service.admin;

import com.project.tklembackend.model.Parent;
import com.project.tklembackend.model.Receptor;
import com.project.tklembackend.repository.ParentRepository;
import com.project.tklembackend.repository.ReceptorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminReceptorService {
    private final ReceptorRepository receptorRepository;

    public List<Receptor> getAllReceptors(){
        return receptorRepository.findAll();
    }
}
