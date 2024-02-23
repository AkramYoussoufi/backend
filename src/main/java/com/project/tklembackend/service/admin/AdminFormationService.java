package com.project.tklembackend.service.admin;

import com.project.tklembackend.dto.FormationDTO;
import com.project.tklembackend.model.Formation;
import com.project.tklembackend.repository.FormationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminFormationService {
    private final FormationRepository formationRepository;
    public Formation addFormation(FormationDTO formationDto) {
        Formation formation = new Formation();
        formation.setName(formationDto.getName());
        formationRepository.save(formation);
        return formation;
    }
    public List<FormationDTO> getAllFormations() {
        List<FormationDTO> list = new ArrayList<>();
        formationRepository.findAll().forEach(
                item -> {
                    list.add(
                            new FormationDTO(item.getId(),item.getName())
                    );
                }
        );
        return list;
    }
    public Formation editFormations(FormationDTO formationDto) {
        Formation formation = new Formation();
        formation.setName(formationDto.getName());
        formation.setId(formationDto.getId());
        return formationRepository.save(formation);
    }
    public void deleteFormations(Long id) {
        formationRepository.deleteById(id);
    }
    public void deleteAllFormations() {
        formationRepository.deleteAll();
    }
}
