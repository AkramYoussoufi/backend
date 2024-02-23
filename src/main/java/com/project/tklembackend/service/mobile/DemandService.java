package com.project.tklembackend.service.mobile;

import com.project.tklembackend.dto.DemandDTO;
import com.project.tklembackend.model.Demand;
import com.project.tklembackend.model.Parent;
import com.project.tklembackend.model.Student;
import com.project.tklembackend.model.UserEntity;
import com.project.tklembackend.repository.*;
import com.project.tklembackend.service.GlobalService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Slf4j
public class DemandService {
    private final GlobalService globalService;
    private final StudentRepository studentRepository;
    private final DemandRepository demandRepository;
    private final ParentRepository parentRepository;
    private final RoleRepository roleRepository;
    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public List<Demand> getAllDemands(){
        return demandRepository.findAll();
    }
    @Transactional
    public ResponseEntity<Map<String, String>> demandAddParent(DemandDTO demandDTO) throws InstanceAlreadyExistsException {
        if(!demandRepository.existsByEmail(demandDTO.getEmail()) || !demandRepository.existsByCin(demandDTO.getCin())){
            List<Student> students = demandDTO.getStudentsMassarCode().stream().map(
                    code-> studentRepository.findByMassarCode(code).orElseThrow(()-> new NoSuchElementException("the demand from the parent about the student he try to add doesnt exist"))
            ).toList();
            Demand demand = new Demand();
            demand.setStudents(students);
            demand.setName(demandDTO.getName());
            demand.setCin(demandDTO.getCin());
            demand.setEmail(demandDTO.getEmail());
            demand.setPassword(demandDTO.getPassword());
            demandRepository.save(demand);
            return new ResponseEntity<>(globalService.responseBuilder("demand for parent approval has been sent successfully"), HttpStatus.CREATED);
        }
        throw new InstanceAlreadyExistsException("this demand has been already sent please wait for the approval of your account");
    }
    @Transactional
    public ResponseEntity<Map<String, String>> acceptAddParent(Long id) {
        Parent parent = new Parent();
        Demand demand = demandRepository.findById(id).get();
        log.warn(demand.getStudents().size()+" ");
        parent.setName(demand.getName());
        parent.setCin(demand.getCin());
        parent.setStudents(demand.getStudents());
        parent.setEmail(demand.getEmail());
        parent.getStudents().forEach(student -> {
            studentRepository.findById(student.getId()).get().getParents().add(parent);
        });
        Parent savedParent = parentRepository.save(parent);
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(bCryptPasswordEncoder.encode(demand.getPassword()));
        userEntity.setRole(roleRepository.findById(4L).get());
        userEntity.setParent(savedParent);
        userEntity.setEnabled(true);
        userEntityRepository.save(userEntity);
        demandRepository.delete(demand);
        return new ResponseEntity<>(globalService.responseBuilder("the parent has been successfully accepted"), HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<Map<String, String>> declineAddParent(Long id) {
        demandRepository.deleteById(id);
        return new ResponseEntity<>(globalService.responseBuilder("the parent has been successfully declined"), HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<Map<String, Long>> sizeOfDemands() {
        HashMap<String,Long> response = new HashMap<>();
        response.put("size",demandRepository.count());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
