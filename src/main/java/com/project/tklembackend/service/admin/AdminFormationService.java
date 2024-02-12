package com.project.tklembackend.service.admin;

import com.project.tklembackend.dto.FormationDto;
import com.project.tklembackend.model.Formation;
import com.project.tklembackend.model.Reciever;
import com.project.tklembackend.repository.FormationRepository;
import com.project.tklembackend.repository.RecieverRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminFormationService {
    private final FormationRepository formationRepository;
    private final RecieverRepository recieverRepository;
    public Formation addFormation(FormationDto formationDto) {
        Formation formation = new Formation();
        formation.setName(formationDto.getName());
        formationRepository.save(formation);
        return formation;
    }
}
