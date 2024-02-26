package com.project.tklembackend.service.mobile;

import com.project.tklembackend.dto.DemandDTO;
import com.project.tklembackend.dto.StudentDTO;
import com.project.tklembackend.model.Demand;
import com.project.tklembackend.model.Parent;
import com.project.tklembackend.model.Student;
import com.project.tklembackend.model.UserEntity;
import com.project.tklembackend.repository.*;
import com.project.tklembackend.service.AuthService;
import com.project.tklembackend.service.GlobalService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.*;
import java.util.stream.Collectors;

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
    private final AuthService authService;

    @Transactional
    public List<DemandDTO> getAllDemands() {
        return demandRepository.findAll().stream().map(
                demand -> {
                    DemandDTO demandDTO = new DemandDTO();
                    demandDTO.setName(demand.getName());
                    demandDTO.setId(demand.getId());

                    // Collect the stream into a List<StudentDTO>
                    List<StudentDTO> studentDTOList = demand.getStudents().stream().map(
                            student -> {
                                StudentDTO studentDTO = new StudentDTO();
                                studentDTO.setId(student.getId());
                                studentDTO.setName(student.getName());
                                studentDTO.setMassarCode(student.getMassarCode());
                                studentDTO.setFormationName(student.getFormation().getName());
                                return studentDTO;
                            }
                    ).collect(Collectors.toList());

                    // Convert List<StudentDTO> to ArrayList<StudentDTO>
                    ArrayList<StudentDTO> arrayListStudentDTO = new ArrayList<>(studentDTOList);

                    demandDTO.setStudents(arrayListStudentDTO); // Set the ArrayList<StudentDTO>
                    return demandDTO;
                }
        ).collect(Collectors.toList());
    }
    @Transactional
    public ResponseEntity<Map<String, String>> demandAddParent(DemandDTO demandDTO) throws InstanceAlreadyExistsException {
        if (parentRepository.existsByEmail(demandDTO.getEmail())) {
            Parent parent = parentRepository.findByEmail(demandDTO.getEmail()).get();
            List<Student> students = demandDTO.getStudents().stream().map(
                    code-> studentRepository.findByMassarCode(code.getMassarCode()).orElseThrow(()-> new NoSuchElementException("the demand from the parent about the student he try to add doesnt exist"))
            ).toList();
            Demand demand;
            if(demandRepository.existsByEmail(parent.getEmail())){
                demand = demandRepository.findByEmail(parent.getEmail());
                students.forEach(
                        student -> {
                            demand.getStudents().add(student);
                        }
                );
            }else{
                demand = new Demand();
                demand.setStudents(students);
                demand.setName(parent.getName());
                demand.setCin(parent.getCin());
                demand.setEmail(parent.getEmail());
                demand.setPassword(parent.getUserEntity().getPassword());
            }
            demandRepository.save(demand);
        }else {
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
            }else{
                throw new InstanceAlreadyExistsException("this demand has been already sent please wait for the approval of your account");
            }
        }
        return null;
    }

    @Transactional
    public ResponseEntity<Map<String, String>> acceptAddParent(Long id) {
        Parent parent;
        Demand demand = demandRepository.findById(id).get();
        if(parentRepository.existsByEmail(demand.getEmail())){
            parent = parentRepository.findByEmail(demand.getEmail()).get();
            demand.getStudents().forEach((item)->{
                studentRepository.findByMassarCode(item.getMassarCode()).get().getParents().add(parent);
                parent.getStudents().add(item);
            });
            parentRepository.save(parent);
            demandRepository.delete(demand);
            return new ResponseEntity<>(globalService.responseBuilder("the parent has been successfully updated"), HttpStatus.OK);
        }else{
            parent = new Parent();
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
            userEntity.setEmail(parent.getEmail());
            userEntity.setEnabled(true);
            userEntityRepository.save(userEntity);
            demandRepository.delete(demand);
            return new ResponseEntity<>(globalService.responseBuilder("the parent has been successfully accepted"), HttpStatus.OK);
        }
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

    public void sendDemandForAddingStudent(String massarCode) throws InstanceAlreadyExistsException {
        if(studentRepository.existsByMassarCode(massarCode)){
            Student student = studentRepository.findByMassarCode(massarCode).get();
            Parent parent = parentRepository.findByEmail(authService.getCurrentAuthenticatedUser().get().getEmail()).get();
            parent.getStudents().forEach(item->{
                if(Objects.equals(item.getMassarCode(), massarCode)){
                    try {
                        throw new InstanceAlreadyExistsException();
                    } catch (InstanceAlreadyExistsException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            DemandDTO demandDTO = new DemandDTO();
            demandDTO.setEmail(parent.getEmail());
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setName(student.getName());
            studentDTO.setId(student.getId());
            studentDTO.setFormationName(student.getFormation().getName());
            studentDTO.setMassarCode(student.getMassarCode());
            demandDTO.getStudents().add(studentDTO);
            this.demandAddParent(demandDTO);
        }else{
            throw new NoSuchElementException("L'étudiant que vous essayez d'ajouter à votre relation n'existe pas dans notre base de données, veuillez vérifier votre MassarCode ou contactez-nous");
        }

    }
}
