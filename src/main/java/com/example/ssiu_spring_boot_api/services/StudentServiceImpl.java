package com.example.ssiu_spring_boot_api.services;

import com.example.ssiu_spring_boot_api.dtos.StudentDTO;
import com.example.ssiu_spring_boot_api.models.StudentEntity;
import com.example.ssiu_spring_boot_api.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentDTO save(StudentDTO StudentDTO) {
        StudentEntity studentEntity = StudentDTO.toStudentEntity();
        studentEntity.setState(true);
        studentEntity.setState_app(true);
        return new StudentDTO(studentRepository.save(studentEntity));
    }

    @Override
    public List<StudentDTO> saveAll(List<StudentDTO> personEntities) {
        return personEntities.stream()
                             .map(StudentDTO::toStudentEntity)
                             .peek(studentRepository::save)
                             .map(StudentDTO::new)
                             .toList();
    }

    @Override
    public List<StudentDTO> findAll() {
        return studentRepository.findAll().stream().map(StudentDTO::new).toList();
    }

    @Override
    public List<StudentDTO> findAll(List<String> ids) {
        return studentRepository.findAll(ids).stream().map(StudentDTO::new).toList();
    }

    @Override
    public StudentDTO findOne(String id) {
        return new StudentDTO(studentRepository.findOne(id));
    }

    @Override
    public long count() {
        return studentRepository.count();
    }

    @Override
    public long delete(String id) {
        return studentRepository.delete(id);
    }

    @Override
    public long delete(List<String> ids) {
        return studentRepository.delete(ids);
    }

    @Override
    public long deleteAll() {
        return studentRepository.deleteAll();
    }

    @Override
    public StudentDTO update(StudentDTO StudentDTO) {
        return new StudentDTO(studentRepository.update(StudentDTO.toStudentEntity()));
    }

    @Override
    public long update(List<StudentDTO> studentEntities) {
        return studentRepository.update(studentEntities.stream().map(StudentDTO::toStudentEntity).toList());
    }

    @Override
    public long activateState(String id) {
        StudentEntity studentEntity = studentRepository.findOne(id);
        if (studentEntity != null) {
            studentEntity.setState(true);
            studentRepository.update(studentEntity);
            return 1;
        }
        return 0;
    }

    @Override
    public long activateStateApp(String id) {
        StudentEntity studentEntity = studentRepository.findOne(id);
        if (studentEntity != null) {
            studentEntity.setState_app(true);
            studentRepository.update(studentEntity);
            return 1;
        }
        return 0;
    }

    @Override
    public long logicalDeleteState(String id) {
        StudentEntity studentEntity = studentRepository.findOne(id);
        if (studentEntity != null) {
            studentEntity.setState(false);
            studentRepository.update(studentEntity);
            return 1;
        }
        return 0;
    }

    @Override
    public long logicalDeleteStateApp(String id) {
        StudentEntity studentEntity = studentRepository.findOne(id);
        if (studentEntity != null) {
            studentEntity.setState_app(false);
            studentRepository.update(studentEntity);
            return 1;
        }
        return 0;
    }

    
}