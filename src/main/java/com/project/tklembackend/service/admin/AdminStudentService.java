package com.project.tklembackend.service.admin;

import com.project.tklembackend.dto.StudentRequestDto;
import com.project.tklembackend.dto.StudentResponseDto;
import com.project.tklembackend.model.Formation;
import com.project.tklembackend.model.Student;
import com.project.tklembackend.repository.FormationRepository;
import com.project.tklembackend.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AdminStudentService {
    private final StudentRepository studentRepository;
    private final FormationRepository formationRepository;

    @Transactional
    public List<StudentResponseDto> getAllStudent(){
        ArrayList<StudentResponseDto> studentResponseDtoArrayList = new ArrayList<>();
        studentRepository.findAll().forEach(
                student -> {
                    StudentResponseDto response = new StudentResponseDto();
                    response.setId(student.getId());
                    response.setName(student.getName());
                    response.setFormationName(student.getFormation().getName());
                    response.setMassarCode(student.getMassarCode());
                    LocalDate localDate = student.getCreatedOn().atZone(ZoneId.systemDefault()).toLocalDate();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formattedDate = formatter.format(localDate);
                    response.setCreated(formattedDate);
                    studentResponseDtoArrayList.add(response);
                }
        );
        return studentResponseDtoArrayList;
    }

    @Transactional
    public Student addStudent(StudentRequestDto studentRequestDto) throws InstanceAlreadyExistsException {
        if(!studentRepository.existsByMassarCode(studentRequestDto.getMassarCode())){
            Student student = new Student();
            student.setName(studentRequestDto.getName());
            student.setMassarCode(studentRequestDto.getMassarCode());
            Formation formation = formationRepository.findByName(studentRequestDto.getFormationName()).orElseThrow(
                    () -> new NoSuchElementException("Student formation doesn't exist")
            );
            student.setFormation(formation);
            return studentRepository.save(student);
        }
        throw new InstanceAlreadyExistsException("Massar Code of this student already exist");
    }

    @Transactional
    public Student editStudent(StudentRequestDto studentRequestDto) {
        if(studentRepository.existsById(studentRequestDto.getId())){
            Student student = StudentDTOtoStudent(studentRequestDto);
            studentRepository.save(student);

            return student;
        }
        throw new NoSuchElementException("element you trying to modify doesn't exist");
    }

    @Transactional
    public void deleteStudent(Long id) {
        try{
            studentRepository.delete(studentRepository.findById(id).get());
        }catch (Exception e){
            throw new NoSuchElementException("element you trying to delete doesn't exist");
        }
    }

    @Transactional
    public void deleteAllStudent(ArrayList<Long> response) {
        try{
            if(response.size() == studentRepository.count()){
                studentRepository.deleteAll();
            }else{
                studentRepository.deleteAllById(response);
            }
        }catch (Exception e){
            throw new NoSuchElementException("element you trying to delete doesn't exist");
        }
    }

    private Student StudentDTOtoStudent(StudentRequestDto requestDto){
        Student student = new Student();
        if(requestDto.getId() != null){
            student.setId(requestDto.getId());
        }
        student.setName(requestDto.getName());
        Formation formation = formationRepository.findByName(requestDto.getFormationName()).orElseThrow(
                () -> new NoSuchElementException("Student formation doesn't exist")
        );
        student.setFormation(formation);
        student.setMassarCode(requestDto.getMassarCode());

        studentRepository.save(student);
        return student;
    }
}
