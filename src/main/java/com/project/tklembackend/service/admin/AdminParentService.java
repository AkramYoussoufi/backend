package com.project.tklembackend.service.admin;

import com.project.tklembackend.dto.ParentDTO;
import com.project.tklembackend.model.Parent;
import com.project.tklembackend.model.Role;
import com.project.tklembackend.model.Student;
import com.project.tklembackend.model.UserEntity;
import com.project.tklembackend.repository.ParentRepository;
import com.project.tklembackend.repository.RoleRepository;
import com.project.tklembackend.repository.StudentRepository;
import com.project.tklembackend.repository.UserEntityRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminParentService {

    private final ParentRepository parentRepository;
    private final UserEntityRepository userEntityRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public List<ParentDTO> getAllParent() {
        return parentRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional
    public void addParent(ParentDTO parentDTO) {
        try {
            Parent parent = convertToEntity(parentDTO);
            if (!parentRepository.existsByCin(parentDTO.getCin())) {
                findStudentByNamesAndSaveNewParent(parentDTO, parent);
            } else {
                throw new InstanceAlreadyExistsException("Parent you are trying to add already exists");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void deleteParent(Long id) {
        UserEntity userEntity = parentRepository.findById(id).get().getUserEntity();
        Parent parent = parentRepository.findById(id).get();
        userEntityRepository.delete(userEntity);
        parent.getStudents().forEach(
                student -> {
                    student.getParents().remove(parent);
                    studentRepository.save(student);
                }
        );
        parentRepository.delete(parent);
    }

    @Transactional
    public void editParent(ParentDTO parentDTO) {
        try {
            if (parentRepository.existsById(parentDTO.getId())) {
                Parent existingParent = parentRepository.findById(parentDTO.getId()).get();
                updateParentFromDTO(existingParent, parentDTO);
                parentRepository.save(existingParent);
            } else {
                throw new NoSuchElementException("Parent you are trying to edit doesn't exist");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void deleteAllParent(List<Map<String, Long>> request) {
        request.forEach(parent -> deleteParent(parent.get("id")));
    }

    @Transactional
    public void findStudentByNamesAndSaveNewParent(ParentDTO parentDTO, Parent parent) {
        parent.setStudents(new ArrayList<>());
        parentDTO.getStudentsNames().stream()
                .map(studentRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(student -> {
                    parent.getStudents().add(student);
                    student.getParents().add(parent); // Maintain the relationship on the student side
                });
        Parent savedParent = parentRepository.save(parent);

        UserEntity user = new UserEntity();
        Role role = roleRepository.findById(4L).get();
        user.setRole(role);
        user.setPassword(bCryptPasswordEncoder.encode(parentDTO.getPassword()));
        user.setEnabled(parentDTO.getStatus());
        user.setEmail(parentDTO.getEmail());
        user.setParent(savedParent);
        userEntityRepository.save(user);
    }

    private Parent convertToEntity(ParentDTO parentDTO) {
        return modelMapper.map(parentDTO, Parent.class);
    }

    private ParentDTO convertToDTO(Parent parent) {
        ParentDTO parentDTO = modelMapper.map(parent, ParentDTO.class);
        parentDTO.setPassword(parent.getUserEntity().getPassword());
        Set<String> studentsNames = parent.getStudents().stream().map(
                Student::getName
        ).collect(Collectors.toSet());
        parentDTO.setStudentsNames(studentsNames);
        parentDTO.setStatus(parent.getUserEntity().getEnabled());
        parentDTO.setId(parent.getId());
        return parentDTO;
    }

    private void updateParentFromDTO(Parent parent, ParentDTO parentDTO) {
        parent.setName(parentDTO.getName());
        parent.setCin(parentDTO.getCin());

        // Remove parent from old students and clear existing list
        parent.getStudents().forEach(student -> {
            student.getParents().remove(parent);
        });
        parent.getStudents().clear();

        // Add new students from DTO and update relationships
        parentDTO.getStudentsNames().stream()
                .map(studentRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(student -> {
                    parent.getStudents().add(student);
                    if (!student.getParents().contains(parent)) {
                        student.getParents().add(parent);
                    }
                    studentRepository.save(student);
                });
    }

    public void editParentPassword(ParentDTO parentDTO) {
        UserEntity user = this.userEntityRepository.findByEmail(parentDTO.getEmail()).get();
        user.setPassword(this.bCryptPasswordEncoder.encode(parentDTO.getPassword()));
        userEntityRepository.save(user);
    }
}